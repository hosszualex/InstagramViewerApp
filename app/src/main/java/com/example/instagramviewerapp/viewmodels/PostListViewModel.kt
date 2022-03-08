package com.example.instagramviewerapp.viewmodels

import android.util.Log
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.repositories.ISocialMediaPostsRepository

class PostListViewModel : BaseViewModel() {
    fun onRetrievePosts() {
        _isBusy.value = true
        socialMediaRepository.getPosts(object : ISocialMediaPostsRepository.IOnGetSocialMediaPosts {
            override fun onSuccess(posts: List<SocialMediaPost>) {
                _onGetPosts.value = posts
                _isBusy.value = false
            }

            override fun onFailed(error: ErrorResponse) {
                Log.d("====LOG===>", "IS NOT GOOD")
                _isBusy.value = false
            }
        })
    }
}