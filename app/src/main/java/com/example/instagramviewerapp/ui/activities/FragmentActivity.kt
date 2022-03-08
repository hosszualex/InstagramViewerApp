package com.example.instagramviewerapp.ui.activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.instagramviewerapp.R

val FragmentActivity.supportFragmentManagerTransaction: FragmentTransaction
    get() {
        return this.supportFragmentManager.beginTransaction()
    }

fun FragmentActivity.addFragmentOnTop(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .replace(R.id.fragmentContainer, fragment, tag)
        .addToBackStack(tag)
        .setReorderingAllowed(true)
        .commit()
}

