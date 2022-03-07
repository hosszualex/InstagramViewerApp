package com.example.instagramviewerapp.ui.activities


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.ActivityMainBinding
import com.example.instagramviewerapp.ui.fragments.PostListFragment
import com.example.instagramviewerapp.viewmodels.MainViewModel

class MainActivity : FragmentActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        addFragmentOnTop(PostListFragment(), Constants.POST_LIST_SCREEN_TAG)
    }

}