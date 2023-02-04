package com.example.cosplayyoutobe.ui.screen.search

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.data.model.search.Item
import com.example.cosplayyoutobe.databinding.FragmentSearchBinding
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.ui.adapter.VideoSearchAdapter
import com.example.cosplayyoutobe.utils.KeyboardUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private var listItemVideoSearch: ArrayList<Item> = ArrayList()
    private lateinit var videoSearchAdapter: VideoSearchAdapter

    override fun getContentLayout(): Int {
        return R.layout.fragment_search
    }

    override fun initView() {
        setupAdapter()
    }

    override fun initListener() {
        binding.toolbar.edtSearch.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(
                editText: TextView?,
                actionId: Int,
                p2: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (binding.toolbar.edtSearch.text.trim().isNotEmpty()) {
                        listItemVideoSearch.clear()
                        videoSearchAdapter.notifyDataSetChanged()
                        val keySearch = binding.toolbar.edtSearch.text.trim()
                        searchViewModel.getListVideoSearch(keySearch.toString())
                    }
                    KeyboardUtils.hideKeyboard(activity)
                    return true
                }
                return false
            }
        })

        binding.toolbar.icClearText.setOnClickListener {
            binding.toolbar.edtSearch.setText("")
            KeyboardUtils.hideKeyboard(activity)
            binding.toolbar.edtSearch.clearFocus()
        }
    }

    override fun observerLiveData() {
        searchViewModel.apply {
            listVideoSearch.observe(this@SearchFragment) { result ->
                handleResult(
                    result,
                    onSuccess = {
                        listItemVideoSearch.addAll(it.items)
                        videoSearchAdapter.notifyDataSetChanged()
                    }
                )
            }
        }
    }

    private fun setupAdapter() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvResultSearch.layoutManager = layoutManager
        videoSearchAdapter = VideoSearchAdapter(
            listItemVideoSearch,
            requireContext(),
            onClickItemVideoListener = { position, videoItem ->
                navigationManager.gotoWatchVideoActivityFromVideoSearch(videoItem)
            })
        binding.rvResultSearch.adapter = videoSearchAdapter
    }

    override fun getLayoutLoading(): View? {
        return null
    }
}