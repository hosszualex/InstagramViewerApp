package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.GetPostsResponse

object IInstagramRetrofitFakeService: IInstagramRetrofitService {

    var mockData: GetPostsResponse? = null
    var responseCode: Int = 200

    override fun getPosts(
        accessToken: String,
        listener: IInstagramRetrofitService.IOnGetInstagramPosts
    ) {
        when (responseCode) {
            200 -> {
                if (mockData != null) {
                    listener.onSuccess(mockData!!)
                }
            }
            190 -> {
                listener.onFailed(ErrorResponse("Access token has expired", 190))
            }
            368 -> {
                listener.onFailed(ErrorResponse("Temporarily blocked for policies violations", 368))
            }
            400 -> {
                listener.onFailed(ErrorResponse("Server is unreachable", 400))
            }
        }
    }

    override fun getChildrenForPost(
        accessToken: String,
        postId: String,
        listener: IInstagramRetrofitService.IOnGetInstagramPosts
    ) {
        //TODO("Not yet implemented")
    }
}