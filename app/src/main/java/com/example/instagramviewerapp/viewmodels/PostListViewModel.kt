package com.example.instagramviewerapp.viewmodels

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.repositories.ISocialMediaPostsRepository
import com.example.instagramviewerapp.repositories.InstagramRepositoryImpl

class PostListViewModel : BaseViewModel() {
    private val socialMediaRepository: ISocialMediaPostsRepository = InstagramRepositoryImpl()

    fun retrievePosts() {
        _isBusy.value = true
        socialMediaRepository.getPosts(object : ISocialMediaPostsRepository.IOnGetSocialMediaPosts {
            override fun onSuccess(posts: List<SocialMediaPost>) {
                _onGetPosts.value = posts
                _isBusy.value = false
            }

            override fun onFailed(error: ErrorResponse) {
                _isBusy.value = false
                _onError.value = error
            }
        })
    }

    fun onRefreshedClicked() {
        retrievePosts()
    }

}