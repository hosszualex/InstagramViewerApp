package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.enums.MediaTypeEnum
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.GetPostsResponse

object IInstagramRetrofitFakeService : IInstagramRetrofitService {

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
            10 -> {
                listener.onFailed(ErrorResponse("API Permission Denied", 10))
            }
            17 -> {
                listener.onFailed(ErrorResponse("API User Too Many Calls", 17))
            }
            190 -> {
                listener.onFailed(ErrorResponse("Access token has expired", 190))
            }
            341 -> {
                listener.onFailed(ErrorResponse("Application limit reached", 341))
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
        when (responseCode) {
            200 -> {
                mockData?.data?.forEach { post ->
                    if (post.media_type == MediaTypeEnum.CAROUSEL_ALBUM.name) {
                        post.children?.let { listener.onSuccess(it) }
                    }
                }
            }
            10 -> {
                listener.onFailed(ErrorResponse("API Permission Denied", 10))
            }
            17 -> {
                listener.onFailed(ErrorResponse("API User Too Many Calls", 17))
            }
            190 -> {
                listener.onFailed(ErrorResponse("Access token has expired", 190))
            }
            341 -> {
                listener.onFailed(ErrorResponse("Application limit reached", 341))
            }
            368 -> {
                listener.onFailed(ErrorResponse("Temporarily blocked for policies violations", 368))
            }
            400 -> {
                listener.onFailed(ErrorResponse("Server is unreachable", 400))
            }
        }
    }
}