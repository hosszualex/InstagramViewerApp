package com.example.instagramviewerapp.repositories

import com.example.instagramviewerapp.Constants
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
        InstagramRetrofitService.getPosts(Constants.ACCESS_TOKEN, object: IOnGetInstagramPosts{
            override fun onSuccess(data: InstagramMediaPostData) {
                handleSuccessCase(data, socialMediaPosts, listener)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    override fun getChildrenForPost(postId: String, listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts) {
        val socialMediaPosts = mutableListOf<SocialMediaPost>()
        InstagramRetrofitService.getChildrenForPost(Constants.ACCESS_TOKEN, postId, object: IOnGetInstagramPosts{
            override fun onSuccess(data: InstagramMediaPostData) {
                handleSuccessCase(data, socialMediaPosts, listener)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    private fun handleSuccessCase(
        data: InstagramMediaPostData,
        socialMediaPosts: MutableList<SocialMediaPost>,
        listener: ISocialMediaPostsRepository.IOnGetSocialMediaPosts
    ) {
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
        listener.onSuccess(socialMediaPosts.toList())
    }
}