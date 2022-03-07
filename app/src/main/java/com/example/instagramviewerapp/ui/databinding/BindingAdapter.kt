package com.example.instagramviewerapp.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:srcUrl")
fun ImageView.setImageResourceFromUrl(url: String) {
    Picasso.get().load(url).into(this)
}

