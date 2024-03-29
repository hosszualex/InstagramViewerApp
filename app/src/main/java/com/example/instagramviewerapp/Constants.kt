package com.example.instagramviewerapp

object Constants {
    //=====API CONSTANTS=====//
    const val INSTAGRAM_API_URL = "https://graph.instagram.com/v13.0/"
    const val ACCESS_TOKEN = "" // todo: place the appropriate instagram access token
    const val GRANT_TYPE = "ig_refresh_token"
    const val POST_FIELD = "id,caption,media_type,media_url,permalink,thumbnail_url,username,timestamp,children"
    const val CHILDREN_FIELD = "id,media_type,media_url,permalink,thumbnail_url,username,timestamp"

    //=====FRAGMENT TAGS=====//
    const val POST_LIST_SCREEN_TAG = "POST_LIST_SCREEN_TAG"
    const val POST_DETAILS_SCREEN_TAG = "POST_DETAILS_SCREEN_TAG"

    //=====ERROR CODES=====//
    const val SERVER_CALL_FAILED_ERROR_CODE = 400
    const val ACCESS_TOKEN_EXPIRED_ERROR_CODE = 190

    //=====OTHER CONSTANTS=====//
    const val FORMAT_OF_DATE = "dd MMMM yyyy"
}