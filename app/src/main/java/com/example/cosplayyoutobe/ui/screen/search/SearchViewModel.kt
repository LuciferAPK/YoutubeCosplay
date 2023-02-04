package com.example.cosplayyoutobe.ui.screen.search

import androidx.lifecycle.ViewModel
import com.example.cosplayyoutobe.BuildConfig
import com.example.cosplayyoutobe.base.Result
import com.example.cosplayyoutobe.base.SingleLiveEvent
import com.example.cosplayyoutobe.data.model.channels.ChannelResponse
import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
import com.example.cosplayyoutobe.data.model.search.SearchResponse
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.data.model_realm.VideoPlaylist
import com.example.cosplayyoutobe.network.END_POINT_GET_CHANNEL_ID
import com.example.cosplayyoutobe.network.END_POINT_GET_PLAYLIST_VIDEO
import com.example.cosplayyoutobe.network.END_POINT_GET_VIDEO_SEARCH
import com.example.cosplayyoutobe.repository.HomeRepository
import com.example.cosplayyoutobe.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    val listVideoSearch = SingleLiveEvent<Result<SearchResponse>>()
    fun getListVideoSearch(q: String) {
        val request = searchRepository.getListVideoSearch(
            url = END_POINT_GET_VIDEO_SEARCH, part = "snippet" , regionCode = "VN",
            maxResult = 50, q = q, type = "video", api_key = BuildConfig.API_KEY,
        )
        listVideoSearch.addSource(request) {
            listVideoSearch.postValue(it)
        }
    }
}