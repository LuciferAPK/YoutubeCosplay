//package com.example.cosplayyoutobe.ui.screen.detail
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.cosplayyoutobe.BuildConfig
//import com.example.cosplayyoutobe.R
//import com.example.cosplayyoutobe.data.model.playlist.Item
//import com.example.cosplayyoutobe.data.model.playlist.PlayListItemResponse
//import com.example.cosplayyoutobe.data.model_realm.VideoLiked
//import com.example.cosplayyoutobe.databinding.ActivityDetailBinding
//import com.example.cosplayyoutobe.databinding.ActivityWatchVideoBinding
//import com.example.cosplayyoutobe.navigation.KeyDataIntent
//import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_ITEM
//import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_NAME_ITEM
//import com.example.cosplayyoutobe.navigation.NavigationManager
//import com.example.cosplayyoutobe.network.Api
//import com.example.cosplayyoutobe.network.END_POINT_GET_PLAYLIST_VIDEO
//import com.example.cosplayyoutobe.ui.adapter.YoutubeVideoAdapter
//import com.google.android.youtube.player.YouTubeBaseActivity
//import com.google.android.youtube.player.YouTubeInitializationResult
//import com.google.android.youtube.player.YouTubePlayer
//import com.google.gson.GsonBuilder
//import io.reactivex.schedulers.Schedulers
//import io.realm.Realm
//import io.realm.RealmResults
//import io.realm.Sort
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//
//class DetailActivity : YouTubeBaseActivity() {
//    private var realm: Realm? = null
//
//    private val navigationManager by lazy {
//        NavigationManager(this)
//    }
//    private lateinit var binding: ActivityDetailBinding
//    private lateinit var youtubePlayer: YouTubePlayer.OnInitializedListener
//
//    private var videoID: String = ""
//    private var videoTitle: String = ""
//    private var videoPublished: String = ""
//    private var videoImg: String = ""
//    private lateinit var youtubeVideoAdapter: YoutubeVideoAdapter
//    private lateinit var layoutManager: LinearLayoutManager
//    private var listItemResponse: ArrayList<Item> = ArrayList()
//    var isLike = false
//    var isDisLike = false
//    var isSaveToRealm = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        /** Realm */
//        realm = Realm.getDefaultInstance()
//
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        getDataListVideo()
//        setupWatchVideo()
//        binding.youtubePlayer.initialize(BuildConfig.API_KEY, youtubePlayer)
//        getDataFromBundle()
//        setupView()
//        initListener()
//        setupAdapter()
//    }
//
//    private fun getDataListVideo() {
//        val retrofit: Api = Retrofit.Builder()
//            .baseUrl(BuildConfig.DEFAULT_API_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .build()
//            .create(Api::class.java)
//
//        val retrofitData = retrofit.getPlayList2Video(
//            url = END_POINT_GET_PLAYLIST_VIDEO, part = "snippet" , playlistId = BuildConfig.PLAY_LIST_2,
//            key = BuildConfig.API_KEY, maxResults = 50)
//
//        retrofitData.enqueue(object : Callback<PlayListItemResponse> {
//            override fun onResponse(
//                call: Call<PlayListItemResponse>,
//                response: Response<PlayListItemResponse>
//            ) {
//                val responseBody = response.body()
//                if (responseBody != null) {
//                    listItemResponse.addAll(responseBody.items)
//                    youtubeVideoAdapter.notifyDataSetChanged()
//                }
//            }
//
//            override fun onFailure(call: Call<PlayListItemResponse>, t: Throwable) {
//                Toast.makeText(this@DetailActivity, "Call Api Failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun initListener() {
////        binding.imgLike.setOnClickListener {
////            icLikeVideoChange()
////        }
////        binding.imgDislike.setOnClickListener {
////            icDisLikeVideoChange()
////        }
////        binding.imgShare.setOnClickListener {
////            startActivityShare(videoImg)
////        }
////        binding.imgDownload.setOnClickListener {
////            Toast.makeText(this, "Cannot Download Video", Toast.LENGTH_SHORT).show()
////        }
//    }
//
//    private fun setupView() {
////        binding.txtNameVideo.text = videoTitle
////        binding.txtPublishedAt.text = videoPublished
//    }
//
//    private fun setupAdapter() {
//        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        binding.rvListVideo.layoutManager = layoutManager
//        youtubeVideoAdapter = YoutubeVideoAdapter(
//            listItemResponse,
//            this,
//            onClickItemVideoListener = { _, videoItem ->
//                navigationManager.gotoWatchVideoActivity(videoItem)
//            })
//        binding.rvListVideo.adapter = youtubeVideoAdapter
//    }
//
////    private fun icLikeVideoChange() {
////        isLike = if (isLike) {
////            binding.imgLike.setImageDrawable(
////                ContextCompat.getDrawable(this, R.drawable.like_down)
////            )
////            if (isSaveToRealm) {
////                deleteVideoLiked(findFirst(videoID))
////            }
////            false
////        } else {
////            binding.imgLike.setImageDrawable(
////                ContextCompat.getDrawable(this, R.drawable.like_up)
////            )
////            if (!isSaveToRealm) {
////                saveVideoLikedToRealm()
////            }
////            true
////        }
////    }
////
////    private fun icDisLikeVideoChange() {
////        isDisLike = if (isDisLike) {
////            binding.imgDislike.setImageDrawable(
////                ContextCompat.getDrawable(this, R.drawable.dislike_down)
////            )
////            false
////        } else {
////            binding.imgDislike.setImageDrawable(
////                ContextCompat.getDrawable(this, R.drawable.dislike_up)
////            )
////            true
////        }
////    }
//
//    override fun onResume() {
//        super.onResume()
//        getDataFromBundle()
//    }
//
//    private fun getDataFromBundle() {
//        videoID = intent.getStringExtra(VIDEO_ITEM).toString()
//        videoTitle = intent.getStringExtra(VIDEO_NAME_ITEM).toString()
//        videoPublished = intent.getStringExtra(KeyDataIntent.VIDEO_PUBLISHED).toString()
//        videoImg = intent.getStringExtra(KeyDataIntent.VIDEO_IMAGE).toString()
//
////        for (item in getVideoLiked()) {
////            if (item.videoId == videoID) {
////                isSaveToRealm = true
////                binding.imgLike.setImageDrawable(
////                    ContextCompat.getDrawable(this, R.drawable.like_up)
////                )
////                return
////            } else {
////                isSaveToRealm = false
////                binding.imgLike.setImageDrawable(
////                    ContextCompat.getDrawable(this, R.drawable.like_down)
////                )
////            }
////        }
//    }
//
//    private fun setupWatchVideo() {
//        youtubePlayer = object : YouTubePlayer.OnInitializedListener {
//            override fun onInitializationSuccess(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubePlayer?,
//                p2: Boolean
//            ) {
//                p1?.loadVideo(videoID)
//            }
//
//            override fun onInitializationFailure(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubeInitializationResult?
//            ) {
//                Log.d("TAG", "onInitializationFailure: p0: ${p0.toString()} \np1: ${p1?.name}")
//            }
//        }
//    }
//
//    private fun findFirst(videoId: String): VideoLiked {
//        return realm?.where(VideoLiked::class.java)?.equalTo("videoId", videoId)?.findFirst() as VideoLiked
//    }
//
//    private fun deleteVideoLiked(videoLiked: VideoLiked) {
//        realm?.beginTransaction()
//        videoLiked.deleteFromRealm()
//        realm?.commitTransaction()
//        realm?.close()
//    }
//
//    private fun getVideoLiked(): List<VideoLiked> {
//        return realm?.where(VideoLiked::class.java)
//            ?.sort("createTime", Sort.DESCENDING)
//            ?.limit(50)
//            ?.findAllAsync() as RealmResults<VideoLiked>
//    }
//
//    private fun saveVideoLikedToRealm() {
//        val videoLiked = VideoLiked()
//        videoLiked.videoId = videoID
//        videoLiked.videoTitle = videoTitle
//        videoLiked.videoImg = videoImg
//        videoLiked.createTime = System.currentTimeMillis()
//        videoLiked.videoPublished = videoPublished
//        realm?.executeTransaction { realm ->
//            realm.copyToRealm(videoLiked)
//        }
//    }
//}