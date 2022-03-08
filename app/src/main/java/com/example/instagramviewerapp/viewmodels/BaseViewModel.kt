package com.example.instagramviewerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost

abstract class BaseViewModel : ViewModel() {
    protected val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy
    protected val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError
    protected val _onGetPosts = MutableLiveData<List<SocialMediaPost>>()
    val onGetPosts: LiveData<List<SocialMediaPost>>
        get() = _onGetPosts

}