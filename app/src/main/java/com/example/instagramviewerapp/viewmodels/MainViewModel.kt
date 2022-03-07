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

class MainViewModel : ViewModel() {
    private var socialMediaRepository: ISocialMediaPostsRepository = InstagramRepositoryImpl()
    private val _isBusy = SingleLiveEvent<Boolean>()

    //TODO make the loading thing
    val isBusy: LiveData<Boolean>
        get() = _isBusy
}