package com.example.instagramviewerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.instagramviewerapp.databinding.FragmentPostDetailsBinding
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.adapters.PostImageAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog
import com.example.instagramviewerapp.viewmodels.PostDetailsViewModel


class PostDetailsFragment(private val post: SocialMediaPost): Fragment() {
    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var viewModel: PostDetailsViewModel
    private lateinit var adapter: PostImageAdapter
    private lateinit var loadingDialog: LoadingDialog
    private var rootView: View? = null

    private val onGetPosts = Observer<List<SocialMediaPost>> { posts ->
        if (!this::adapter.isInitialized) {
            adapter = PostImageAdapter()
            val helper: SnapHelper = LinearSnapHelper()
            helper.attachToRecyclerView(binding.rvImages)
            binding.rvImages.adapter = adapter
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
            binding.item = post
            binding.executePendingBindings()
            rootView = binding.root
            loadingDialog = LoadingDialog(requireActivity())
        }

        return rootView
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