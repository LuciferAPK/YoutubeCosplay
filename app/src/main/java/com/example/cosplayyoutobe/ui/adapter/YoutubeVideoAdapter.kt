package com.example.cosplayyoutobe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cosplayyoutobe.data.model.video.Item
import com.example.cosplayyoutobe.databinding.ImageItemBinding
import com.example.cosplayyoutobe.ui.viewholder.PlayListViewHolder

class YoutubeVideoAdapter(
    private val listAdapter: ArrayList<Item>,
    private val context: Context,
    private val onClickItemVideoListener: (Int, Item) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayListViewHolder(binding, onClickItemVideoListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlayListViewHolder).bind(listAdapter[position], position, context)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }
}