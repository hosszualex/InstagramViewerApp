package com.example.instagramviewerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramviewerapp.databinding.FragmentPostDetailsBinding
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.adapters.PostsAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog
import com.example.instagramviewerapp.viewmodels.PostDetailsViewModel

class PostDetailsFragment(private val post: SocialMediaPost): Fragment() {
    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var viewModel: PostDetailsViewModel
    private var rootView: View? = null
    private lateinit var adapter: PostsAdapter
    private lateinit var loadingDialog: LoadingDialog

    private val onGetPosts = Observer<List<SocialMediaPost>> { posts ->
        if (!this::adapter.isInitialized) {
            adapter = PostsAdapter()
            binding.rvPosts.adapter = adapter
        }
        adapter.setDataSource(posts)
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
        viewModel = ViewModelProvider(this).get(PostDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            binding = FragmentPostDetailsBinding.inflate(inflater)
            binding.lifecycleOwner = this
            binding.executePendingBindings()
            rootView = binding.root
            loadingDialog = LoadingDialog(requireActivity())
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.isBusy.observe(this, isBusy)
        viewModel.onGetPosts.observe(this, onGetPosts)
        viewModel.onRetrieveChildren(post.id)
    }

    override fun onPause() {
        super.onPause()
        viewModel.isBusy.removeObserver(isBusy)
        viewModel.onGetPosts.removeObserver(onGetPosts)
    }

}