package com.example.instagramviewerapp.viewmodels

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.repositories.ISocialMediaPostsRepository
import com.example.instagramviewerapp.repositories.InstagramRepositoryImpl

class PostDetailsViewModel : BaseViewModel() {
    private val socialMediaRepository: ISocialMediaPostsRepository = InstagramRepositoryImpl()

    fun retrieveChildrenPosts(postId: String) {
        _isBusy.value = true
        socialMediaRepository.getChildrenForPost(
            postId,
            object : ISocialMediaPostsRepository.IOnGetSocialMediaPosts {
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

    fun onRefreshedClicked(postId: String) {
        retrieveChildrenPosts(postId)
    }
}