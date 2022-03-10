package com.example.instagramviewerapp

object Constants {
    //=====API CONSTANTS=====//
    const val INSTAGRAM_API_URL = "https://graph.instagram.com/v13.0/"
    const val ACCESS_TOKEN = "IGQVJVQnhZAX1lPUG1IU3VNTFNnWm1EUXBGMU4wT1ZALRmYyTVdDNGFqczR1YkU0b19fUzFqY05zVU9GZAXZA1d0cwdTduNG1VZAWRRdC1rSGU0YzdZAVzM1VnZAfeHlXUmh4OFBRcFRZAT2djazlSMGg4SV9XWAZDZD"
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