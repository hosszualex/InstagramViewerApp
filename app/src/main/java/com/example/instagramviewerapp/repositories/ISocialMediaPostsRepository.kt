package com.example.instagramviewerapp.repositories

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost

interface ISocialMediaPostsRepository {

    fun getPosts(listener: IOnGetSocialMediaPosts)

    fun getChildrenForPost(postId: String, listener: IOnGetSocialMediaPosts)

    interface IOnGetSocialMediaPosts {
        fun onSuccess(posts: List<SocialMediaPost>)
        fun onFailed(error: ErrorResponse)
    }
}