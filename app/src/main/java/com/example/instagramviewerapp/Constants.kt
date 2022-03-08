package com.example.instagramviewerapp

object Constants {
    const val INSTAGRAM_API_URL = "https://graph.instagram.com/v13.0/"
    //TODO ENCRYPT THIS
    const val ACCESS_TOKEN = "IGQVJVU2tiM2MxcjlnM2dTTk1nX3U5X0xxbXNLaURaVHptYlJ0YmpSLURGck15dC1aRUdRMlVxVENERDBsOGZAjakFJLTEwNUFOSFliOFdUWXZAPWGtiRnRleUIwSUNVaDVtM0dBNzhB"
    const val GRANT_TYPE = "ig_refresh_token"
    const val SERVER_CALL_FAILED_ERROR_CODE = 400
    const val ACCESS_TOKEN_EXPIRED_ERROR_CODE = 190
    const val POST_FIELD = "id,caption,media_type,media_url,permalink,thumbnail_url,username,timestamp,children"
    const val CHILDREN_FIELD = "id,media_type,media_url,permalink,thumbnail_url,username,timestamp"
    const val FORMAT_OF_DATE = "dd MMMM yyyy"

    const val POST_LIST_SCREEN_TAG = "POST_LIST_SCREEN_TAG"
    const val POST_DETAILS_SCREEN_TAG = "POST_DETAILS_SCREEN_TAG"
}