package com.example.instagramviewerapp.repositories

import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.enums.toMediaTypeEnum
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.models.GetPostsResponse
import com.example.instagramviewerapp.services.IInstagramRetrofitService
import com.example.instagramviewerapp.services.InstagramRetrofitService
import com.example.instagramviewerapp.utils.Utils

class InstagramRepositoryImpl: ISocialMediaPostsRepository {

    override fun getPosts(listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts) {
        InstagramRetrofitService.getPosts(Constants.ACCESS_TOKEN, object:
            IInstagramRetrofitService.IOnGetInstagramPosts {
            override fun onSuccess(data: GetPostsResponse) {
                val socialMediaPosts = getSocialMediaPostsFromResponse(data)
                listener.onSuccess(socialMediaPosts)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    override fun getChildrenForPost(postId: String, listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts) {
        InstagramRetrofitService.getChildrenForPost(Constants.ACCESS_TOKEN, postId, object:
            IInstagramRetrofitService.IOnGetInstagramPosts {
            override fun onSuccess(data: GetPostsResponse) {
                val socialMediaPosts = getSocialMediaPostsFromResponse(data)
                listener.onSuccess(socialMediaPosts)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    private fun getSocialMediaPostsFromResponse(data: GetPostsResponse, ): List<SocialMediaPost> {
        val socialMediaPosts = mutableListOf<SocialMediaPost>()
        data.data.forEach { post ->
            socialMediaPosts.add(
                SocialMediaPost(
                    post.id,
                    post.caption,
                    post.media_url.toString(),
                    post.media_type.toMediaTypeEnum(),
                    Utils.formatDateFromISO8601(post.timestamp.toString())
                )
            )
        }
        socialMediaPosts.sortByDescending { it.postDate }
        return socialMediaPosts.toList()
    }
}