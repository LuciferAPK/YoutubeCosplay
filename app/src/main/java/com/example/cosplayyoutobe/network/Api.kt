package com.example.cosplayyoutobe.network

import com.example.cosplayyoutobe.data.model.channels.ChannelResponse
import com.example.cosplayyoutobe.data.model.comments.CommentsResponse
import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
import com.example.cosplayyoutobe.data.model.search.SearchResponse
import com.example.cosplayyoutobe.data.model.video.RandomVideoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    /**
     * https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId={PLAY_LIST_1_ID}
     * &key={API_KEY}&maxResults=50
     **/
    @GET
    suspend fun getPlayListVideo(
        @Url url: String?,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int
    ) : Response<PlayListItemResponse>

    @GET
    fun getPlayList2Video(
        @Url url: String?,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int
    ) : Call<PlayListItemResponse>


    /** API comment
     * https://www.googleapis.com/youtube/v3/commentThreads?part=snippet&videoId=iLlkrjXHPkE
     * &key=AIzaSyC_AMW_5QjpUiRWSE8EJUW1_hiIUdEcnYs*/
    @GET
    fun getCommentInVideo(
        @Url url: String?,
        @Query("part") part: String,
        @Query("videoId") videoId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int
    ) : Call<CommentsResponse>


    /** API search Video in VN
     * https://www.googleapis.com/youtube/v3/search?part=snippet&regionCode=VN&maxResults=50
     * &q={keyword}&type=video&key=AIzaSyC_AMW_5QjpUiRWSE8EJUW1_hiIUdEcnYs*/
    @GET
    suspend fun getVideoSearch(
        @Url url: String?,
        @Query("part") part: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int,
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("key") key: String
    ) : Response<SearchResponse>

    /** API random Video in VN
     * https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&chart=mostPopular
     * &regionCode=VN&key=AIzaSyC_AMW_5QjpUiRWSE8EJUW1_hiIUdEcnYs&maxResults=50*/
    @GET
    suspend fun getVideoRandom(
        @Url url: String?,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String
    ) : Response<RandomVideoResponse>

    @GET
    fun getVideoRandomInDetail(
        @Url url: String?,
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String
    ) : Call<RandomVideoResponse>

    /** API Channel
     * https://www.googleapis.com/youtube/v3/channels?part=snippet,contentDetails,statistics
     * &id=UCfprwrOYCpHRQydrVNlsqrw&key=AIzaSyC_AMW_5QjpUiRWSE8EJUW1_hiIUdEcnYs*/
    @GET
    suspend fun getChannel(
        @Url url: String?,
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") key: String
    ) : Response<ChannelResponse>
}