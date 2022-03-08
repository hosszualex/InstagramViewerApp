package com.example.instagramviewerapp.repositories

import com.example.instagramviewerapp.enums.toMediaTypeEnum
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.models.InstagramMediaPostData
import com.example.instagramviewerapp.services.IOnGetInstagramPosts
import com.example.instagramviewerapp.services.InstagramRetrofitService
import com.example.instagramviewerapp.utils.Utils

class InstagramRepositoryImpl: ISocialMediaPostsRepository {

    override fun getPosts(listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts) {
        val socialMediaPosts = mutableListOf<SocialMediaPost>()
        InstagramRetrofitService.getPosts(object: IOnGetInstagramPosts{
            override fun onSuccess(data: InstagramMediaPostData) {
                data.data.forEach { post ->
                    socialMediaPosts.add(
                        SocialMediaPost(post.id, post.caption, post.media_url.toString(), post.media_type.toMediaTypeEnum(),Utils.formatDateFromISO8601(post.timestamp.toString()))
                    )
                }

               listener.onSuccess(socialMediaPosts.toList())
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    override fun getChildrenForPost(postId: String, listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts) {
        val socialMediaPosts = mutableListOf<SocialMediaPost>()
        InstagramRetrofitService.getChildrenForPost(postId, object: IOnGetInstagramPosts{
            override fun onSuccess(data: InstagramMediaPostData) {
                data.data.forEach { post ->
                    socialMediaPosts.add(
                        SocialMediaPost(post.id, post.caption, post.media_url.toString(), post.media_type.toMediaTypeEnum(), Utils.formatDateFromISO8601(post.timestamp.toString()))
                    )
                }

                listener.onSuccess(socialMediaPosts.toList())
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }
}