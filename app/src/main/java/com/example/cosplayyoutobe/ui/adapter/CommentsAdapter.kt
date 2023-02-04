package com.example.cosplayyoutobe.ui.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseRecyclerAdapter
import com.example.cosplayyoutobe.data.model.comments.Item
import com.example.cosplayyoutobe.databinding.ItemCommentsBinding
import com.example.cosplayyoutobe.ui.viewholder.ViewHolderLifecycle

class CommentsAdapter(
    listAdapter: MutableList<Item?>?,
    private val context: Context
) : BaseRecyclerAdapter<Item, CommentsAdapter.ViewHolder>(listAdapter) {

    override fun getLayoutResourceItem(): Int {
        return R.layout.item_comments
    }

    override fun setViewHolderLifeCircle(): ViewHolderLifecycle? = null

    override fun onCreateBasicViewHolder(binding: ViewDataBinding?): ViewHolder {
        return ViewHolder(binding as ItemCommentsBinding)
    }

    override fun onBindBasicItemView(holder: ViewHolder, position: Int) {
        val model: Item? = getDataSet()?.get(position)
        holder.fillData(model)
    }

    inner class ViewHolder(val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun fillData(model: Item?) {
            binding.txtNameUser.text = model?.snippet?.topLevelComment?.snippet?.authorDisplayName
            binding.txtComment.text = model?.snippet?.topLevelComment?.snippet?.textDisplay
            binding.txtTimeComment.text = model?.snippet?.topLevelComment?.snippet?.publishedAt?.substring(0, 10)
            Glide.with(context)
                .load(model?.snippet?.topLevelComment?.snippet?.authorProfileImageUrl)
                .into(binding.imgUserComment)
            binding.txtLike.text = model?.snippet?.topLevelComment?.snippet?.likeCount.toString()
        }
    }
}