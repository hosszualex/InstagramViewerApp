package com.example.instagramviewerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SnapHelper
import com.example.instagramviewerapp.databinding.FragmentPostDetailsBinding
import com.example.instagramviewerapp.models.ErrorResponse
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.adapters.PostImageAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog
import com.example.instagramviewerapp.utils.SnapHelperOneItem
import com.example.instagramviewerapp.viewmodels.PostDetailsViewModel

class PostDetailsFragment(private val post: SocialMediaPost): Fragment() {
    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var viewModel: PostDetailsViewModel
    private lateinit var adapter: PostImageAdapter
    private lateinit var loadingDialog: LoadingDialog
    private var rootView: View? = null

    private val onGetPosts = Observer<List<SocialMediaPost>> { posts ->
        if (!this::adapter.isInitialized) {
            initializeAdapter()
        }
        adapter.setDataSource(posts)
    }

    private fun initializeAdapter() {
        adapter = PostImageAdapter()
        val helper: SnapHelper = SnapHelperOneItem()
        helper.attachToRecyclerView(binding.rvImages)
        binding.rvImages.adapter = adapter
    }

    private val isBusy = Observer<Boolean> { isBusy ->
        if (isBusy) {
            loadingDialog.startDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private val onError = Observer<ErrorResponse> { onError ->
        Toast.makeText(requireContext(), "Error Message: " + onError.errorMessage + "\nError Code: " + onError.errorCode, Toast.LENGTH_LONG).show()
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
        initializeScreen(inflater)
        connectViewModel()
        viewModel.onRetrieveChildren(post.id)
        return rootView
    }

    private fun initializeScreen(inflater: LayoutInflater) {
        binding = FragmentPostDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.item = post
        binding.viewModel = viewModel
        binding.executePendingBindings()
        rootView = binding.root
        loadingDialog = LoadingDialog(requireActivity())
    }

    private fun connectViewModel() {
        viewModel.isBusy.observe(viewLifecycleOwner, isBusy)
        viewModel.onGetPosts.observe(viewLifecycleOwner, onGetPosts)
        viewModel.onError.observe(viewLifecycleOwner, onError)
    }
}