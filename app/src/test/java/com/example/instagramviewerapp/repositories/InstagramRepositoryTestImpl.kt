package com.example.instagramviewerapp.repositories

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.instagramviewerapp.FakeConstants
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.GetPostsResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.services.IInstagramRetrofitFakeService
import com.example.instagramviewerapp.viewmodels.PostDetailsViewModel
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

    @Before
    fun setupRepository() {
        instagramRepository = InstagramRepositoryImpl()
        method = instagramRepository.javaClass.getDeclaredMethod("getSocialMediaPostsFromResponse", GetPostsResponse::class.java)
        method.isAccessible = true
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
}