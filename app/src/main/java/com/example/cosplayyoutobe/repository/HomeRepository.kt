package com.example.cosplayyoutobe.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.cosplayyoutobe.base.Result
import com.example.cosplayyoutobe.data.model.channels.ChannelResponse
import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
import com.example.cosplayyoutobe.data.model.video.RandomVideoResponse
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.data.model_realm.VideoPlaylist
import com.example.cosplayyoutobe.local.RealmManager
import com.example.cosplayyoutobe.network.Api
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.math.max

class HomeRepository @Inject constructor(
    private val api: Api,
    private val realmManager: RealmManager
) {
    companion object {
        private val TAG = HomeRepository::class.simpleName
    }

    fun getAllListVideoItem(
        url: String?,
        part: String,
        playListId: String,
        api_key: String,
        maxResult: Int
    ): LiveData<Result<PlayListItemResponse>> = liveData(Dispatchers.IO) {
        emit(Result.InProgress())
        try {
            val request = api.getPlayListVideo(
                url = url,
                part = part,
                playlistId = playListId,
                key = api_key,
                maxResults = maxResult
            )
            if (request.isSuccessful) {
                emit(Result.Success(request.body() as PlayListItemResponse))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getVideoRandom(
        url: String?,
        part: String,
        chart: String,
        regionCode: String,
        maxResult: Int,
        api_key: String
    ): LiveData<Result<RandomVideoResponse>> = liveData(Dispatchers.IO) {
        emit(Result.InProgress())
        try {
            val request = api.getVideoRandom(
                url = url,
                part = part,
                chart = chart,
                regionCode = regionCode,
                maxResults = maxResult,
                key = api_key
            )
            if (request.isSuccessful) {
                emit(Result.Success(request.body() as RandomVideoResponse))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getChannel(
        url: String?,
        part: String,
        id: String,
        api_key: String
    ): LiveData<Result<ChannelResponse>> = liveData(Dispatchers.IO) {
        emit(Result.InProgress())
        try {
            val request = api.getChannel(
                url = url,
                part = part,
                id = id,
                key = api_key
            )
            if (request.isSuccessful) {
                emit(Result.Success(request.body() as ChannelResponse))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun saveVideoPlaylist(videoLiked: VideoLiked) {
        Realm.getDefaultInstance().beginTransaction()
        videoLiked.createTime = System.currentTimeMillis()
        Realm.getDefaultInstance().commitTransaction()
        realmManager.save(videoLiked)
    }

    fun getVideoLiked(): List<VideoLiked> {
        return realmManager.findAllSortedAsync(
            VideoLiked::class.java,
            "createTime",
            Sort.DESCENDING,
        ) as RealmResults<VideoLiked>
    }

    fun saveVideoHistory(videoHistory: VideoHistory) {
        Realm.getDefaultInstance().beginTransaction()
        videoHistory.createTime = System.currentTimeMillis()
        Realm.getDefaultInstance().commitTransaction()
        realmManager.save(videoHistory)
    }

    fun getVideoHistory(): List<VideoHistory> {
        return realmManager.findAllSortedAsync(
            VideoHistory::class.java,
            "createTime",
            Sort.DESCENDING,
            ) as RealmResults<VideoHistory>
    }

    fun saveVideoPlaylist(videoPlaylist: VideoPlaylist) {
        Realm.getDefaultInstance().beginTransaction()
        videoPlaylist.createTime = System.currentTimeMillis()
        Realm.getDefaultInstance().commitTransaction()
        realmManager.save(videoPlaylist)
    }

    fun getVideoPlaylist(): List<VideoPlaylist> {
        return realmManager.findAllSortedAsync(
            VideoPlaylist::class.java,
            "createTime",
            Sort.DESCENDING,
        ) as RealmResults<VideoPlaylist>
    }

//    fun removeVideoLiked(videoLiked: VideoLiked) {
//        Realm.getDefaultInstance().beginTransaction()
//        Realm.getDefaultInstance().commitTransaction()
//        realmManager.save(videoLiked)
//    }
}