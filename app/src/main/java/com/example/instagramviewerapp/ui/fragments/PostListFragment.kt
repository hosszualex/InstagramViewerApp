package com.example.instagramviewerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramviewerapp.databinding.FragmentPostListBinding
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.adapters.PostsAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog
import com.example.instagramviewerapp.viewmodels.PostListViewModel

class PostListFragment: Fragment(), PostsAdapter.IOnPostClickListener {
    private lateinit var binding: FragmentPostListBinding
    private lateinit var viewModel: PostListViewModel
    private lateinit var adapter: PostsAdapter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(inflater)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    private val isBusy = Observer<Boolean> { isBusy ->
        if (isBusy) {
            loadingDialog.startDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private val onGetPosts = Observer<List<SocialMediaPost>> { posts ->
        if (!this::adapter.isInitialized) {
            adapter = PostsAdapter(this)
            binding.rvPosts.adapter = adapter
        }
        adapter.setDataSource(posts)
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

    override fun onPostClicked() {
        //TODO("Not yet implemented")
    }
}