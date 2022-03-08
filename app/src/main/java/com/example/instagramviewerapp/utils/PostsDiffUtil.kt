package com.example.instagramviewerapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.instagramviewerapp.models.SocialMediaPost

class PostsDiffUtil
    (
    private val oldList: List<SocialMediaPost>,
    private val newList: List<SocialMediaPost>
    ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].caption != newList[newItemPosition].caption -> {
                false
            }
            oldList[oldItemPosition].mediaURL != newList[newItemPosition].mediaURL -> {
                false
            }
            oldList[oldItemPosition].mediaType != newList[newItemPosition].mediaType -> {
                false
            }
            oldList[oldItemPosition].postDate != newList[newItemPosition].postDate -> {
                false
            }
            else -> {
                true
            }
        }
    }

}