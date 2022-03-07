package com.example.instagramviewerapp.models

//TODO Try to make this generic
data class InstagramMediaPostData(
    val data: ArrayList<GetPostsResponse>,
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