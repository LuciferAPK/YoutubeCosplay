package com.example.cosplayyoutobe.ui.screen.lib.fragment

import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model.playlist.Item
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.databinding.FragmentHistoryBinding
import com.example.cosplayyoutobe.databinding.FragmentVideoLikedBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.adapter.HistoryLibAdapter
import com.example.cosplayyoutobe.ui.adapter.VideoLikedLibAdapter
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoLikedFragment : BaseFragment<FragmentVideoLikedBinding>() {
    @Inject
    lateinit var navigationManager: NavigationManager

    private var listItemResponse: ArrayList<VideoLiked> = ArrayList()
    private lateinit var videoLikedLibAdapter: VideoLikedLibAdapter

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getContentLayout(): Int {
        return R.layout.fragment_video_liked
    }

    override fun initView() {
        getVideoLiked()
        setupAdapter()
    }

    override fun initListener() {
        onBackStack()
    }

    override fun observerLiveData() {

    }

    override fun getLayoutLoading(): View? {
        return null
    }

    private fun setupAdapter() {
        videoLikedLibAdapter = VideoLikedLibAdapter(
            listItemResponse as MutableList<VideoLiked?>,
            requireContext(),
            onClickItem = { video ->
                if (video != null) {
                    navigationManager.gotoWatchVideoActivityFromVideoLiked(video = video)
                }
            })
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvVideoLiked.adapter = videoLikedLibAdapter
        binding.rvVideoLiked.layoutManager = layoutManager
    }

    private fun onBackStack() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            })
    }

    private fun getVideoLiked() {
        listItemResponse.addAll(mainViewModel.getVideoLiked())
    }
}