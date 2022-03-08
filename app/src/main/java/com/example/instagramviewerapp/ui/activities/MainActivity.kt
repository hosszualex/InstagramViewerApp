package com.example.instagramviewerapp.ui.activities


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.instagramviewerapp.Constants
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.ActivityMainBinding
import com.example.instagramviewerapp.extensions.addFragmentOnTop
import com.example.instagramviewerapp.extensions.lastFragment
import com.example.instagramviewerapp.ui.fragments.PostListFragment

class MainActivity : FragmentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        addFragmentOnTop(PostListFragment(), Constants.POST_LIST_SCREEN_TAG)
    }

    override fun onBackPressed() {
        val lastFragment = this.lastFragment()
        if (lastFragment is PostListFragment) {
            finish()
        }else {
            super.onBackPressed()
        }
    }
}