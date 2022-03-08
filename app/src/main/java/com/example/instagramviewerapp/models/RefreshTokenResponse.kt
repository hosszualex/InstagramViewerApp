package com.example.instagramviewerapp.models

data class RefreshTokenResponse(
    var access_token: String,
    var token_type: String,
    var expires_in: Int
)