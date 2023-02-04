package com.example.cosplayyoutobe.ui.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cosplayyoutobe.data.model.search.Item
import com.example.cosplayyoutobe.databinding.ImageItemBinding

class VideoSearchViewHolder(
    private val binding: ImageItemBinding,
    private val onClickItemVideoListener: (Int, Item) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(playListItemResponse: Item, position: Int, context: Context) =
        binding.apply {
            Glide.with(context)
                .load(playListItemResponse.snippet.thumbnails.high.url)
                .into(binding.imageItem)
            binding.imageName.text = playListItemResponse.snippet.title
            binding.root.setOnClickListener {
                onClickItemVideoListener.invoke(position, playListItemResponse)
            }
        }
}