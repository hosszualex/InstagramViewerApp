package com.example.instagramviewerapp.enums

enum class MediaTypeEnum {
    IMAGE,
    VIDEO,
    CAROUSEL_ALBUM,
    NONE
}

fun String?.toMediaTypeEnum(): MediaTypeEnum {
    return when (this) {
        "IMAGE" -> MediaTypeEnum.IMAGE
        "VIDEO" -> MediaTypeEnum.VIDEO
        "CAROUSEL_ALBUM" -> MediaTypeEnum.CAROUSEL_ALBUM
        else -> MediaTypeEnum.NONE
    }
}