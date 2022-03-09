package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.GetPostsResponse

interface IInstagramRetrofitService {

    fun getPosts(accessToken: String, listener: IOnGetInstagramPosts)

    fun getChildrenForPost(accessToken: String, postId: String, listener: IOnGetInstagramPosts)

    interface IOnRefreshToken {
        fun onSuccess(refreshedAccessToken: String)
        fun onFailed(error: ErrorResponse)
    }

    interface IOnGetInstagramPosts {
        fun onSuccess(data: GetPostsResponse)
        fun onFailed(error: ErrorResponse)
    }
}