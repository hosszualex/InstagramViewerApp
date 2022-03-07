package com.example.instagramviewerapp.ui.activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.instagramviewerapp.R

private var commandCallback: (() -> Unit)? = null

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

fun FragmentActivity.addFragmentOnTopWithAnimationLeftToRight(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .setCustomAnimations(
            R.anim.left_to_right,
            R.anim.left_to_right_end,
            R.anim.right_to_left,
            R.anim.right_to_left_end
        )
        .replace(R.id.fragmentContainer, fragment, tag)
        .addToBackStack(tag)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentActivity.addFragmentOnTopWithAnimationRightToLeft(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .setCustomAnimations(
            R.anim.right_to_left,
            R.anim.right_to_left_end,
            R.anim.left_to_right,
            R.anim.left_to_right_end
        )
        .replace(R.id.fragmentContainer, fragment, tag)
        .addToBackStack(tag)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction.replace(R.id.fragmentContainer, fragment, tag).commit()
}

fun FragmentActivity.replaceFragmentWithAnimationRightToLeft(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .setCustomAnimations(
            R.anim.right_to_left,
            R.anim.right_to_left_end,
            R.anim.left_to_right,
            R.anim.left_to_right_end
        ).replace(R.id.fragmentContainer, fragment, tag).commit()
}


fun FragmentActivity.lastFragment(): Fragment? {
    return if (supportFragmentManager.fragments.isEmpty()) {
        null
    } else {
        supportFragmentManager.fragments.last()
    }
}

fun FragmentActivity.setCustomMethod(command: (() -> Unit)?) {
    commandCallback = command
}

fun FragmentActivity.executeCustomMethod() {
    commandCallback?.let { it() }
}
