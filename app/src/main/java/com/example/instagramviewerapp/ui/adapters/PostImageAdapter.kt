package com.example.instagramviewerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramviewerapp.databinding.SocialMediaImageItemBinding
import com.example.instagramviewerapp.models.SocialMediaPost
import com.example.instagramviewerapp.utils.PostsDiffUtil

class PostImageAdapter: RecyclerView.Adapter<PostImageAdapter.ViewHolder>() {

    private var items: List<SocialMediaPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SocialMediaImageItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun setDataSource(newItems: List<SocialMediaPost>) {
        val diffUtil = PostsDiffUtil(this.items, newItems)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.items = newItems
        diffResults.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: SocialMediaImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SocialMediaPost) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}