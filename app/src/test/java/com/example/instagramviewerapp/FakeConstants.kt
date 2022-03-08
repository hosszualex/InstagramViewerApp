package com.example.instagramviewerapp

import com.example.instagramviewerapp.enums.MediaTypeEnum
import com.example.instagramviewerapp.models.GetPostsResponse
import com.example.instagramviewerapp.models.InstagramResponseData
import com.example.instagramviewerapp.models.PagingResponse
import com.example.instagramviewerapp.models.SocialMediaPost

object FakeConstants {
    private val mockData = arrayListOf<InstagramResponseData>(
        InstagramResponseData("1", "Caption 1", "IMAGE", "fake_url1", "fake_permalink1", "fake_username1", "2017-07-11T17:29:17+0000", null),
        InstagramResponseData("2", "Caption 2", "IMAGE", "fake_url2", "fake_permalink2", "fake_username2", "2021-07-11T17:29:17+0000", null),
        InstagramResponseData("3", "Caption 3", "IMAGE", "fake_url3", "fake_permalink3", "fake_username3", "2020-07-11T17:29:17+0000", null),
        InstagramResponseData("4", "Caption 4", "IMAGE", "fake_url4", "fake_permalink4", "fake_username4", "2019-07-11T17:29:17+0000", null),
        InstagramResponseData("5", "Caption 5", "IMAGE", "fake_url5", "fake_permalink5", "fake_username5", "2018-07-11T17:29:17+0000", null)

    )
    val mockResponse = GetPostsResponse(mockData, PagingResponse())

    val expectedMockValue = mutableListOf<SocialMediaPost>(
        SocialMediaPost("2", "Caption 2", "fake_url2", MediaTypeEnum.IMAGE, "11 July 2021"),
        SocialMediaPost("3", "Caption 3", "fake_url3", MediaTypeEnum.IMAGE, "11 July 2020"),
        SocialMediaPost("4", "Caption 4", "fake_url4", MediaTypeEnum.IMAGE, "11 July 2019"),
        SocialMediaPost("5", "Caption 5", "fake_url5", MediaTypeEnum.IMAGE, "11 July 2018"),
        SocialMediaPost("1", "Caption 1", "fake_url1", MediaTypeEnum.IMAGE, "11 July 2017")
    )
}