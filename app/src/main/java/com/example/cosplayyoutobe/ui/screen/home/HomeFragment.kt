package com.example.cosplayyoutobe.ui.screen.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cosplayyoutobe.BuildConfig
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model.video.Item
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoPlaylist
import com.example.cosplayyoutobe.databinding.FragmentHomeBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.adapter.YoutubeVideoAdapter
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var navigationManager: NavigationManager
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var youtubeVideoAdapter: YoutubeVideoAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listItemResponse: ArrayList<Item> = ArrayList()

    override fun getContentLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        setupAdapter()
    }

    override fun initListener() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observerLiveData() {
        mainViewModel.apply {
            listVideoRandomInHome.observe(this@HomeFragment) { result ->
                handleResult(
                    result,
                    onSuccess = {
                        listItemResponse.addAll(it.items)
                        youtubeVideoAdapter.notifyDataSetChanged()
                    }
                )
            }
        }
    }

    override fun getLayoutLoading(): View? {
        return null
    }

    private fun setupAdapter() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvPlayList.layoutManager = layoutManager
        youtubeVideoAdapter = YoutubeVideoAdapter(
            listItemResponse,
            requireContext(),
            onClickItemVideoListener = { position, videoItem ->
                mainViewModel.saveVideoHistory(saveVideoHistoryToRealm(videoItem))
                navigationManager.gotoWatchVideoActivity(videoItem)
            })
        binding.rvPlayList.adapter = youtubeVideoAdapter
    }

    override fun onResume() {
        super.onResume()
        if (listItemResponse.isEmpty()) mainViewModel.getListVideoRandomHome()
    }

    private fun saveVideoHistoryToRealm(item: Item): VideoHistory {
        val videoHistory = VideoHistory()
        videoHistory.videoId = item.id
        videoHistory.videoTitle = item.snippet.title
        videoHistory.videoImg = item.snippet.thumbnails.standard.url
        videoHistory.videoPublished = item.snippet.publishedAt
        videoHistory.viewCount = item.statistics.viewCount
        videoHistory.commentCount = item.statistics.commentCount
        return videoHistory
    }
}