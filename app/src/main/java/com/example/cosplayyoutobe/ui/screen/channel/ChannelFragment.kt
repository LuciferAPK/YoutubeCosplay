package com.example.cosplayyoutobe.ui.screen.channel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model.channels.Item
import com.example.cosplayyoutobe.databinding.FragmentChannelBinding
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelFragment : BaseFragment<FragmentChannelBinding>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var myChannel: ArrayList<Item> = ArrayList()

    override fun getContentLayout(): Int {
        return R.layout.fragment_channel
    }

    override fun initView() {
        mainViewModel.getChannelID()
//        setupView()
    }

    override fun initListener() {

    }

    @SuppressLint("SetTextI18n")
    override fun observerLiveData() {
        mainViewModel.apply {
            channelID.observe(this@ChannelFragment) { result ->
                handleResult(
                    result,
                    onSuccess = {
                        myChannel.addAll(it.items)
                        for (item in myChannel) {
                            Glide.with(this@ChannelFragment)
                                .load(item.snippet.thumbnails.default.url)
                                .into(binding.imgChannel)
                            binding.txtNameChannel.text = item.snippet.title
                            val dayStart = item.snippet.publishedAt
                            binding.txtDayStart.text = "Ngày tạo kênh: ${dayStart.substring(0, 10)}"
                            binding.txtView.text = "Tổng View: ${item.statistics.viewCount}"
                            binding.txtSub.text = "Người đăng kí: ${item.statistics.subscriberCount}"
                            binding.txtVideo.text = "Video đã đăng: ${item.statistics.videoCount}"
                        }
                    }
                )
            }
        }
    }

    private fun setupView() {
        for (item in myChannel) {
            Log.d("TAG", "setupView: ${item.id}")
            Log.d("TAG", "setupView: ${myChannel.size}")
            binding.txtNameChannel.text = item.snippet.title
        }
    }

    override fun getLayoutLoading(): View? {
        return null
    }
}