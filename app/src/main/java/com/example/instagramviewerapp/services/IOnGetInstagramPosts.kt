package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.InstagramMediaPostData

interface IOnGetInstagramPosts {
    fun onSuccess(data: InstagramMediaPostData)
    fun onFailed(error: ErrorResponse)
}