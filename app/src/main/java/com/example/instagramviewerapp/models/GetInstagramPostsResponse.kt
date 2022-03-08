package com.example.instagramviewerapp.models

data class GetInstagramPostsResponse(
    var id: String,
    var caption: String?,
    var media_type: String?,
    var media_url: String?,
    var permalink: String?,
    var username: String?,
    var timestamp: String?,
    var children: InstagramMediaPostData?
)

data class InstagramMediaPostData(
    val data: ArrayList<GetInstagramPostsResponse>,
    val paging: PagingResponse?
)

data class PagingResponse(
    val cursors: CursorResponse?,
    val next: String?
)

data class CursorResponse(
    val before: String,
    val after: String
)