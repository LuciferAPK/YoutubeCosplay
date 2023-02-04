package com.example.cosplayyoutobe.ui.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseRecyclerAdapter
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.databinding.ItemVideoHistoryLibBinding
import com.example.cosplayyoutobe.ui.viewholder.ViewHolderLifecycle

class VideoLikedLibAdapter(
    dataSet: MutableList<VideoLiked?>?,
    private val context: Context,
    private val onClickItem: (VideoLiked?) -> Unit
): BaseRecyclerAdapter<VideoLiked, VideoLikedLibAdapter.ViewHolder>(dataSet) {
    override fun getLayoutResourceItem(): Int {
        return R.layout.item_video_history_lib
    }

    override fun onCreateBasicViewHolder(binding: ViewDataBinding?): ViewHolder {
        return ViewHolder(binding as ItemVideoHistoryLibBinding)
    }

    override fun onBindBasicItemView(holder: ViewHolder, position: Int) {
        val model: VideoLiked? = getDataSet()?.get(position)
        holder.fillData(model, position)
    }

    inner class ViewHolder(val binding: ItemVideoHistoryLibBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun fillData(model: VideoLiked?, position: Int) {
            binding.imageName.text = model?.videoTitle
            Glide.with(context)
                .load(model?.videoImg)
                .into(binding.imageItem)
            binding.root.setOnClickListener {
                onClickItem.invoke(model)
            }
        }
    }

    override fun setViewHolderLifeCircle(): ViewHolderLifecycle? = null
}