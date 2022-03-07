package com.example.instagramviewerapp.ui.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.instagramviewerapp.MainViewModel
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.ActivityMainBinding
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.ui.adapters.PostsAdapter
import com.example.instagramviewerapp.ui.dialogs.LoadingDialog

class MainActivity : AppCompatActivity(), PostsAdapter.IOnPostClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PostsAdapter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        loadingDialog = LoadingDialog(this)
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