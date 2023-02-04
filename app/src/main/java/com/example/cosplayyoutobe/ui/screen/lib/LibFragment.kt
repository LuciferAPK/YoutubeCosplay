package com.example.cosplayyoutobe.ui.screen.lib

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.databinding.FragmentLibBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.adapter.HistoryAdapter
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LibFragment : BaseFragment<FragmentLibBinding>() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var listItemResponse: ArrayList<VideoHistory> = ArrayList()
    private lateinit var historyAdapter: HistoryAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getContentLayout(): Int {
        return R.layout.fragment_lib
    }

    override fun initView() {
        getVideoHistory()
        setupAdapter()
    }

    override fun initListener() {
        binding.historyVideo.setOnClickListener {
            navigationManager.navigationToHistory(parentFragmentManager)
        }
        binding.videoLiked.setOnClickListener {
            navigationManager.navigationToVideoLikedFragment(parentFragmentManager)
        }
    }

    override fun observerLiveData() {

    }

    override fun getLayoutLoading(): View? {
        return null
    }

    private fun setupAdapter() {
        historyAdapter = HistoryAdapter(
            listItemResponse as MutableList<VideoHistory?>,
            requireContext(),
            onClickItem = { video ->
                if (video != null) {
                    navigationManager.gotoWatchVideoActivityFromVideoHistory(video)
                }
            })
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHistoryVideo.adapter = historyAdapter
        binding.rvHistoryVideo.layoutManager = layoutManager
    }

    private fun getVideoHistory() {
        listItemResponse.addAll(mainViewModel.getVideoHistory())
    }
}