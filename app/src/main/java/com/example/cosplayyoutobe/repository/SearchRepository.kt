package com.example.cosplayyoutobe.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.cosplayyoutobe.base.Result
import com.example.cosplayyoutobe.data.model.channels.ChannelResponse
import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
import com.example.cosplayyoutobe.data.model.search.SearchResponse
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

class SearchRepository @Inject constructor(
    private val api: Api
) {
    companion object {
        private val TAG = SearchRepository::class.simpleName
    }

    fun getListVideoSearch(
        url: String?,
        part: String,
        regionCode: String,
        maxResult: Int,
        q: String,
        type: String,
        api_key: String
    ): LiveData<Result<SearchResponse>> = liveData(Dispatchers.IO) {
        emit(Result.InProgress())
        try {
            val request = api.getVideoSearch(
                url = url,
                part = part,
                regionCode = regionCode,
                maxResults = maxResult,
                q = q,
                type = type,
                key = api_key,
            )
            if (request.isSuccessful) {
                emit(Result.Success(request.body() as SearchResponse))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}