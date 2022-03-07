package com.example.instagramviewerapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramviewerapp.SingleLiveEvent
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.repositories.ISocialMediaPostsRepository
import com.example.instagramviewerapp.repositories.InstagramRepositoryImpl

class PostListViewModel : ViewModel() {
    private var socialMediaRepository: ISocialMediaPostsRepository = InstagramRepositoryImpl()
    private val _isBusy = SingleLiveEvent<Boolean>()

    //TODO make the loading thing
    val isBusy: LiveData<Boolean>
        get() = _isBusy
    private val _onGetPosts = MutableLiveData<List<SocialMediaPost>>()
    val onGetPosts: LiveData<List<SocialMediaPost>>
        get() = _onGetPosts


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