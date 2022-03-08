package com.example.instagramviewerapp.models

data class GetPostsResponse(
    val data: ArrayList<InstagramResponseData>,
    val paging: PagingResponse?
)

data class InstagramResponseData(
    var id: String,
    var caption: String?,
    var media_type: String?,
    var media_url: String?,
    var permalink: String?,
    var username: String?,
    var timestamp: String?,
    var children: GetPostsResponse?
)

data class PagingResponse(
    val cursors: CursorResponse? = null,
    val next: String? = null
)

data class CursorResponse(
    val before: String,
    val after: String
)