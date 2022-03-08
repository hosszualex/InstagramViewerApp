package com.example.instagramviewerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.databinding.FragmentPostListBinding
import com.example.instagramviewerapp.enums.MediaTypeEnum
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.activities.addFragmentOnTopWithAnimationLeftToRight
import com.example.instagramviewerapp.ui.adapters.PostsAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog
import com.example.instagramviewerapp.viewmodels.PostListViewModel

class PostListFragment: Fragment(), PostsAdapter.IOnPostClickListener {
    private lateinit var binding: FragmentPostListBinding
    private lateinit var viewModel: PostListViewModel
    private lateinit var adapter: PostsAdapter
    private lateinit var loadingDialog: LoadingDialog
    private var rootView: View? = null

    private val onGetPosts = Observer<List<SocialMediaPost>> { posts ->
        if (!this::adapter.isInitialized) {
            initializeAdapter()
        }
        adapter.setDataSource(posts)
    }

    private fun initializeAdapter() {
        adapter = PostsAdapter(this)
        binding.rvPosts.adapter = adapter
    }

    private val isBusy = Observer<Boolean> { isBusy ->
        if (isBusy) {
            loadingDialog.startDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            initializeScreen(inflater)
        }

        return rootView
    }

    private fun initializeScreen(inflater: LayoutInflater) {
        binding = FragmentPostListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        rootView = binding.root
        loadingDialog = LoadingDialog(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        viewModel.isBusy.observe(this, isBusy)
        viewModel.onGetPosts.observe(this, onGetPosts)
        viewModel.onRetrievePosts()
    }

    override fun onPause() {
        super.onPause()
        viewModel.isBusy.removeObserver(isBusy)
        viewModel.onGetPosts.removeObserver(onGetPosts)
    }

    override fun onPostClicked(post: SocialMediaPost) {
        if (post.mediaType == MediaTypeEnum.CAROUSEL_ALBUM) {
            activity?.addFragmentOnTopWithAnimationLeftToRight(PostDetailsFragment(post), Constants.POST_DETAILS_SCREEN_TAG)
        } else {
            Toast.makeText(activity, "This Post is not a Carousel Album.", Toast.LENGTH_SHORT).show()
        }
    }
}