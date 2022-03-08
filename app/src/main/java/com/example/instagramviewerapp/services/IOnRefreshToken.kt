package com.example.instagramviewerapp.services

import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.InstagramMediaPostData

interface IOnRefreshToken {
    fun onSuccess(refreshedAccessToken: String)
    fun onFailed(error: ErrorResponse)
}