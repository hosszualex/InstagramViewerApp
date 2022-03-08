package com.example.instagramviewerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.repositories.ISocialMediaPostsRepository
import com.example.instagramviewerapp.repositories.InstagramRepositoryImpl

abstract class BaseViewModel : ViewModel() {
    protected var socialMediaRepository: ISocialMediaPostsRepository = InstagramRepositoryImpl()
    protected val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy
    protected val _onGetPosts = MutableLiveData<List<SocialMediaPost>>()
    val onGetPosts: LiveData<List<SocialMediaPost>>
        get() = _onGetPosts

}