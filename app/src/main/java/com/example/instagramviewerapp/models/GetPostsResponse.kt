package com.example.instagramviewerapp.models

data class GetPostsResponse(
    var id: String,
    var caption: String?,
    var media_type: String?,
    var media_url: String?,
    var permalink: String?,
    var username: String?,
    var timestamp: String?,
    var children: InstagramMediaPostData?
)