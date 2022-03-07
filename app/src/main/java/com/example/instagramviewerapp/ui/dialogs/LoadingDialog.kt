package com.example.instagramviewerapp.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.instagramviewerapp.R
import com.example.instagramviewerapp.databinding.DialogLoadingBinding


class LoadingDialog(activity: Activity) {
    private var activity: Activity? = null
    private var dialog: AlertDialog? = null
    private lateinit var binding : DialogLoadingBinding
    val isShowing: Boolean
        get() {
        return dialog?.isShowing == true
    }

    init {
        this.activity = activity
    }

    @SuppressLint("InflateParams")
    fun startDialog() {
        val builder = AlertDialog.Builder(activity, R.style.LoadingDialog)
        binding = DialogLoadingBinding.inflate(LayoutInflater.from(activity))
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}