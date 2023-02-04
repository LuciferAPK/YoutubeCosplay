package com.example.cosplayyoutobe.ui.screen.lib.fragment

import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.databinding.FragmentHistoryBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.adapter.HistoryLibAdapter
import com.example.cosplayyoutobe.ui.screen.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    @Inject
    lateinit var navigationManager: NavigationManager

    private var listItemResponse: ArrayList<VideoHistory> = ArrayList()
    private lateinit var historyLibAdapter: HistoryLibAdapter

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getContentLayout(): Int {
        return R.layout.fragment_history
    }

    override fun initView() {
        getVideoHistory()
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
        historyLibAdapter = HistoryLibAdapter(
            listItemResponse as MutableList<VideoHistory?>,
            requireContext(),
            onClickItem = { video ->
                if (video != null) {
                    navigationManager.gotoWatchVideoActivityFromVideoHistory(video = video)
                }
            })
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHistoryVideo.adapter = historyLibAdapter
        binding.rvHistoryVideo.layoutManager = layoutManager
    }

    private fun onBackStack() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }
            })
    }

    private fun getVideoHistory() {
        listItemResponse.addAll(mainViewModel.getVideoHistory())
    }
}