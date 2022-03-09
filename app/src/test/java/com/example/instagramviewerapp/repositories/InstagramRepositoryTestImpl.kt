package com.example.instagramviewerapp.repositories

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.instagramviewerapp.FakeConstants
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.GetPostsResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.services.IInstagramRetrofitFakeService
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field
import java.lang.reflect.Method

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class InstagramRepositoryTestImpl {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var instagramRepository: ISocialMediaPostsRepository
    private lateinit var method: Method
    private lateinit var fakeRetrofitService: Field

    @Before
    fun setupRepository() {
        instagramRepository = InstagramRepositoryImpl()
        method = instagramRepository.javaClass.getDeclaredMethod("getSocialMediaPostsFromResponse", GetPostsResponse::class.java)
        method.isAccessible = true

        fakeRetrofitService = instagramRepository.javaClass.getDeclaredField("retrofitService")
        fakeRetrofitService.isAccessible = true
        fakeRetrofitService.set(instagramRepository, IInstagramRetrofitFakeService)
    }

    @Test
    fun getPostsFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(instagramRepository, FakeConstants.mockResponse)
        Assert.assertEquals(FakeConstants.expectedMockValue, value)
    }

    @Test
    fun getEmptyPostsFromResponseSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val value = method(instagramRepository, GetPostsResponse(arrayListOf(), null))
        Assert.assertEquals(true, (value as List<SocialMediaPost>).isEmpty())
    }

    @Test
    fun onGetRepositoryPostsSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.mockData = FakeConstants.mockResponse
        IInstagramRetrofitFakeService.responseCode = 200

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
                Assert.assertEquals(FakeConstants.expectedMockValue, posts)
            }

            override fun onFailed(error: ErrorResponse) {}
        })
    }

    @Test
    fun onGetRepositoryPostsFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 400

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
            }
        })
    }

    @Test
    fun onGetRepositoryPostsFailedExpiredAccessTokenTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 190

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Access token has expired", 190), error)
            }
        })
    }

    @Test
    fun onGetRepositoryPostsFailedPermissionDeniedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 10

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("API Permission Denied", 10), error)
            }
        })
    }

    @Test
    fun onGetRepositoryPostsFailedTooManyCallsTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 17

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("API User Too Many Calls", 17), error)
            }
        })
    }

    @Test
    fun onGetRepositoryPostsFailedApplicationLimitReachedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 341

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Application limit reached", 341), error)
            }
        })
    }

    @Test
    fun onGetRepositoryPostsFailedPolicyViolationTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 368

        instagramRepository.getPosts(object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {}

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Temporarily blocked for policies violations", 368), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.mockData = FakeConstants.mockResponse
        IInstagramRetrofitFakeService.responseCode = 200

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
                Assert.assertEquals(FakeConstants.expectedMockChildrenValue, posts)
            }

            override fun onFailed(error: ErrorResponse) {}
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 400

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedExpiredAccessTokenTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 190

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Access token has expired", 190), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedPermissionDeniedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 10

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("API Permission Denied", 10), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedTooManyCallsTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 17

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("API User Too Many Calls", 17), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedApplicationLimitReachedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 341

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Application limit reached", 341), error)
            }
        })
    }

    @Test
    fun onGetRepositoryChildrenPostsFailedPolicyViolationTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 368

        instagramRepository.getChildrenForPost(FakeConstants.mockCarouselId, object: ISocialMediaPostsRepository.IOnGetSocialMediaPosts{
            override fun onSuccess(posts: List<SocialMediaPost>) {
            }

            override fun onFailed(error: ErrorResponse) {
                Assert.assertEquals(ErrorResponse("Temporarily blocked for policies violations", 368), error)
            }
        })
    }
}