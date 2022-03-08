package com.example.instagramviewerapp.ui.activities


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.ActivityMainBinding
import com.example.instagramviewerapp.ui.fragments.PostListFragment

//TODO add an internet check?
class MainActivity : FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        addFragmentOnTop(PostListFragment(), Constants.POST_LIST_SCREEN_TAG)
    }
}