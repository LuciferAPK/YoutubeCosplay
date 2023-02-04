package com.example.cosplayyoutobe.ui.screen.main

import androidx.lifecycle.ViewModel
import com.example.cosplayyoutobe.BuildConfig
import com.example.cosplayyoutobe.base.Result
import com.example.cosplayyoutobe.base.SingleLiveEvent
import com.example.cosplayyoutobe.data.model.channels.ChannelResponse
import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
import com.example.cosplayyoutobe.data.model.video.RandomVideoResponse
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.data.model_realm.VideoPlaylist
import com.example.cosplayyoutobe.network.END_POINT_GET_CHANNEL_ID
import com.example.cosplayyoutobe.network.END_POINT_GET_PLAYLIST_VIDEO
import com.example.cosplayyoutobe.network.END_POINT_GET_VIDEO_RANDOM
import com.example.cosplayyoutobe.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    val listVideoRandomInHome = SingleLiveEvent<Result<RandomVideoResponse>>()
    fun getListVideoRandomHome() {
        val request = homeRepository.getVideoRandom(
            url = END_POINT_GET_VIDEO_RANDOM, part = "snippet,contentDetails,statistics", chart = "mostPopular", regionCode = "VN",
            maxResult = 50, api_key = BuildConfig.API_KEY
        )
        listVideoRandomInHome.addSource(request) {
            listVideoRandomInHome.postValue(it)
        }
    }

//    val listVideoInHome = SingleLiveEvent<Result<PlayListItemResponse>>()
//    fun getListItemHome(playListId: String) {
//        val request = homeRepository.getAllListVideoItem(
//            url = END_POINT_GET_PLAYLIST_VIDEO, part = "snippet" ,playListId = playListId,
//            api_key = BuildConfig.API_KEY, maxResult = 50
//        )
//        listVideoInHome.addSource(request) {
//            listVideoInHome.postValue(it)
//        }
//    }

//    val listVideoInLocal = SingleLiveEvent<Result<PlayListItemResponse>>()
//    fun getListItemLocal(playListId: String) {
//        val request = homeRepository.getAllListVideoItem(
//            url = END_POINT_GET_PLAYLIST_VIDEO, part = "snippet" ,playListId = playListId,
//            api_key = BuildConfig.API_KEY, maxResult = 50
//        )
//        listVideoInLocal.addSource(request) {
//            listVideoInLocal.postValue(it)
//        }
//    }

    /**get Channel ID*/
    val channelID = SingleLiveEvent<Result<ChannelResponse>>()
    fun getChannelID() {
        val request = homeRepository.getChannel(
            url = END_POINT_GET_CHANNEL_ID,
            part = "snippet,contentDetails,statistics" ,
            id = "UCfprwrOYCpHRQydrVNlsqrw",
            api_key = BuildConfig.API_KEY
        )
        channelID.addSource(request) {
            channelID.postValue(it)
        }
    }

    fun saveVideoHistory(videoHistory: VideoHistory) = homeRepository.saveVideoHistory(videoHistory)
    fun getVideoHistory() : List<VideoHistory> = homeRepository.getVideoHistory()

    fun saveVideoPlaylist(videoPlaylist: VideoPlaylist) = homeRepository.saveVideoPlaylist(videoPlaylist)
    fun getVideoPlaylist() : List<VideoPlaylist> = homeRepository.getVideoPlaylist()

    fun saveVideoPlaylist(videoLiked: VideoLiked) = homeRepository.saveVideoPlaylist(videoLiked)
    fun getVideoLiked() : List<VideoLiked> = homeRepository.getVideoLiked()
}