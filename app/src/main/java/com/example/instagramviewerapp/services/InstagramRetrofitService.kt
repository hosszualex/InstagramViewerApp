package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.BuildConfig
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.InstagramMediaPostData
import com.example.instagramviewerapp.models.RefreshTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL
import java.util.concurrent.TimeUnit

//TODO TEST ERROR CASES
object InstagramRetrofitService {
    private val instagramApiService: IInstagramApiService
    var hasRetried = false

    init {
        instagramApiService = createRetrofitInstance().create(IInstagramApiService::class.java)
    }

    private fun createRetrofitInstance(): Retrofit {
        val instagramApiUrl = URL(Constants.INSTAGRAM_API_URL)
        return Retrofit.Builder()
            .client(createHttpClient())
            .baseUrl(instagramApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        lateinit var okHttpClient: OkHttpClient
        val builder: OkHttpClient.Builder = if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logging)
        } else {
            OkHttpClient.Builder()
        }.readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        okHttpClient = builder.build()

        return okHttpClient
    }

    private interface IInstagramApiService {
        @GET("refresh_access_token")
        fun refreshToken(
            @Path("grant_type") grantType: String,
            @Path("access_token") accessToken: String
        ): Call<RefreshTokenResponse>

        @GET("me/media")
        fun getPosts(
            @Query("fields") fields: String,
            @Query("access_token") accessToken: String
        ): Call<InstagramMediaPostData>

        @GET("{postId}/children")
        fun getChildrenForPost(
            @Path("postId") postId: String,
            @Query("fields") fields: String,
            @Query("access_token") accessToken: String
        ): Call<InstagramMediaPostData>
    }


    fun getPosts(accessToken: String, listener: IOnGetInstagramPosts) {
        val request = instagramApiService.getPosts(
            Constants.POST_FIELD,
            accessToken
        )
        request.enqueue(object : Callback<InstagramMediaPostData?> {
            override fun onResponse(
                call: Call<InstagramMediaPostData?>,
                postResponse: Response<InstagramMediaPostData?>
            ) {
                if (postResponse.isSuccessful) {
                    postResponse.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorResponse =
                        ErrorResponse(postResponse.errorBody().toString(), postResponse.code())
                    handlerError(errorResponse)
                }
            }

            override fun onFailure(call: Call<InstagramMediaPostData?>, t: Throwable) {
                val errorResponse =
                    ErrorResponse(t.message.toString(), Constants.SERVER_CALL_FAILED_ERROR_CODE)
                handlerError(errorResponse)
            }

            private fun handlerError(errorResponse: ErrorResponse) {
                if (shouldRefreshToken(errorResponse)) {
                    refreshToken(accessToken, object : IOnRefreshToken {
                        override fun onSuccess(refreshedAccessToken: String) {
                            getPosts(refreshedAccessToken, listener)
                        }

                        override fun onFailed(error: ErrorResponse) {
                            listener.onFailed(error)
                        }
                    })
                } else {
                    listener.onFailed(errorResponse)
                }
            }
        })
    }


    fun getChildrenForPost(
        accessToken: String,
        postId: String,
        listener: IOnGetInstagramPosts
    ) {
        val request = instagramApiService.getChildrenForPost(
            postId,
            Constants.CHILDREN_FIELD,
            Constants.ACCESS_TOKEN
        )
        request.enqueue(object : Callback<InstagramMediaPostData?> {
            override fun onResponse(
                call: Call<InstagramMediaPostData?>,
                postResponse: Response<InstagramMediaPostData?>
            ) {
                if (postResponse.isSuccessful) {
                    postResponse.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorResponse = ErrorResponse(
                        postResponse.errorBody().toString(), postResponse.code()
                    )
                    handlerError(errorResponse)
                }
            }

            override fun onFailure(call: Call<InstagramMediaPostData?>, t: Throwable) {
                val errorResponse = ErrorResponse(
                    t.message.toString(),
                    Constants.SERVER_CALL_FAILED_ERROR_CODE
                )
                handlerError(errorResponse)
            }

            private fun handlerError(errorResponse: ErrorResponse) {
                if (shouldRefreshToken(errorResponse)) {
                    refreshToken(accessToken, object : IOnRefreshToken {
                        override fun onSuccess(refreshedAccessToken: String) {
                            getChildrenForPost(refreshedAccessToken, postId, listener)
                        }

                        override fun onFailed(error: ErrorResponse) {
                            listener.onFailed(error)
                        }
                    })
                } else {
                    listener.onFailed(errorResponse)
                }
            }
        })
    }

    private fun refreshToken(accessToken: String, listener: IOnRefreshToken) {
        val request = instagramApiService.refreshToken(Constants.GRANT_TYPE, accessToken)
        request.enqueue(object : Callback<RefreshTokenResponse> {
            override fun onResponse(
                call: Call<RefreshTokenResponse>,
                postResponse: Response<RefreshTokenResponse>
            ) {
                if (postResponse.isSuccessful) {
                    postResponse.body()?.let { body -> listener.onSuccess(body.access_token) }
                } else {
                    val errorResponse = ErrorResponse(
                        postResponse.errorBody().toString(), postResponse.code()
                    )
                    listener.onFailed(errorResponse)
                }
            }

            override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                val errorResponse = ErrorResponse(
                    t.message.toString(),
                    Constants.SERVER_CALL_FAILED_ERROR_CODE
                )
                listener.onFailed(errorResponse)
            }
        })
    }

    private fun shouldRefreshToken(errorResponse: ErrorResponse): Boolean {
        return if (errorResponse.errorCode == Constants.ACCESS_TOKEN_EXPIRED_ERROR_CODE && !hasRetried) {
            hasRetried = true
            true
        } else {
            hasRetried = false
            false
        }
    }
}