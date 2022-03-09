package com.example.instagramviewerapp.viewModels

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.instagramviewerapp.FakeConstants
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.repositories.InstagramRepositoryFakeImpl
import com.example.instagramviewerapp.services.IInstagramRetrofitFakeService
import com.example.instagramviewerapp.viewmodels.PostListViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import java.lang.reflect.Field

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class PostListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var postListViewModel: PostListViewModel
    private lateinit var fakeSocialMediaRepository: Field

    @Before
    fun setupViewModel() {
        postListViewModel = PostListViewModel()
        fakeSocialMediaRepository = postListViewModel.javaClass.getDeclaredField("socialMediaRepository")
        fakeSocialMediaRepository.isAccessible = true
        fakeSocialMediaRepository.set(postListViewModel, InstagramRepositoryFakeImpl())
    }

    @Test
    fun onGetPostsSuccessTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.mockData = FakeConstants.mockResponse
        IInstagramRetrofitFakeService.responseCode = 200

        postListViewModel.retrievePosts()

        val value = postListViewModel.onGetPosts.value
        Assert.assertEquals(FakeConstants.expectedMockValue, value)
    }

    @Test
    fun onGetPostsFailedUnreachableServerTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 400

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Server is unreachable", 400), error)
    }

    @Test
    fun onGetPostsFailedExpiredAccessTokenTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 190

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Access token has expired", 190), error)
    }

    @Test
    fun onGetPostsFailedPermissionDeniedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 10

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("API Permission Denied", 10), error)
    }

    @Test
    fun onGetPostsFailedTooManyCallsTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 17

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("API User Too Many Calls", 17), error)
    }

    @Test
    fun onGetPostsFailedApplicationLimitReachedTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 341

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Application limit reached", 341), error)
    }

    @Test
    fun onGetPostsFailedPolicyViolationTest() {
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        IInstagramRetrofitFakeService.responseCode = 368

        postListViewModel.retrievePosts()

        val error = postListViewModel.onError.value
        Assert.assertEquals(ErrorResponse("Temporarily blocked for policies violations", 368), error)
    }

}