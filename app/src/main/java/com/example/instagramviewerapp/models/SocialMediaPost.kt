package com.example.instagramviewerapp.models

import com.example.instagramviewerapp.enums.MediaTypeEnum

data class SocialMediaPost(
    val id: String,
    val caption: String? = null,
    val mediaURL: String,
    val mediaType: MediaTypeEnum,
    val postDate: String
)