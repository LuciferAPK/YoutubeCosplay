package com.example.cosplayyoutobe.ui.screen.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cosplayyoutobe.BuildConfig
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.data.model.comments.CommentsResponse
import com.example.cosplayyoutobe.data.model.video.Item
import com.example.cosplayyoutobe.data.model.video.RandomVideoResponse
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.databinding.ActivityWatchVideoBinding
import com.example.cosplayyoutobe.navigation.KeyDataIntent.COMMENT_COUNT
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_IMAGE
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_ITEM
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_NAME_ITEM
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_PUBLISHED
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIEW_COUNT
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.network.Api
import com.example.cosplayyoutobe.network.END_POINT_GET_COMMENT
import com.example.cosplayyoutobe.network.END_POINT_GET_VIDEO_RANDOM
import com.example.cosplayyoutobe.ui.adapter.CommentsAdapter
import com.example.cosplayyoutobe.ui.adapter.YoutubeVideoAdapter
import com.example.cosplayyoutobe.utils.CoroutineExt
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WatchVideoActivity : YouTubeBaseActivity() {

    private var realm: Realm? = null

    private val navigationManager by lazy {
        NavigationManager(this)
    }
    private lateinit var binding: ActivityWatchVideoBinding
    private lateinit var youtubePlayer: YouTubePlayer.OnInitializedListener

    private var videoID: String = ""
    private var videoTitle: String = ""
    private var videoPublished: String = ""
    private var videoImg: String = ""
    private var viewCount: String = ""
    private var commentCount: String = ""
    private lateinit var youtubeVideoAdapter: YoutubeVideoAdapter
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var listItemResponse: ArrayList<Item> = ArrayList()
    private var listCommentsResponse: ArrayList<com.example.cosplayyoutobe.data.model.comments.Item> = ArrayList()
    var isLike = false
    var isDisLike = false
    var isSaveToRealm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Realm */
        realm = Realm.getDefaultInstance()

        binding = ActivityWatchVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromBundle()
        CoroutineExt.runOnMainAfterDelay(1000) {
            getDataInDetail()
        }
        setupWatchVideo()
        binding.youtubePlayer.initialize(BuildConfig.API_KEY, youtubePlayer)
        setupView()
        initListener()
        setupAdapter()
    }

    private fun getDataInDetail() {
        val retrofit: Api = Retrofit.Builder()
            .baseUrl(BuildConfig.DEFAULT_API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(Api::class.java)

        /**get list video*/
        val retrofitPlaylist = retrofit.getVideoRandomInDetail(
            url = END_POINT_GET_VIDEO_RANDOM, part = "snippet,contentDetails,statistics", chart = "mostPopular", regionCode = "VN",
            maxResults = 50, key = BuildConfig.API_KEY)

        retrofitPlaylist.enqueue(object : Callback<RandomVideoResponse>{
            override fun onResponse(
                call: Call<RandomVideoResponse>,
                response: Response<RandomVideoResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    listItemResponse.addAll(responseBody.items)
                    youtubeVideoAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<RandomVideoResponse>, t: Throwable) {
                Toast.makeText(this@WatchVideoActivity, "Call Api Failed", Toast.LENGTH_SHORT).show()
            }
        })

        /**get comment in video*/
        val retrofitComments = retrofit.getCommentInVideo(
            url = END_POINT_GET_COMMENT, part = "snippet" , videoId = videoID,
            key = BuildConfig.API_KEY, maxResults = 50)

        retrofitComments.enqueue(object : Callback<CommentsResponse>{
            override fun onResponse(
                call: Call<CommentsResponse>,
                response: Response<CommentsResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    listCommentsResponse.addAll(responseBody.items)
                    commentsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                Toast.makeText(this@WatchVideoActivity, "Call Api Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initListener() {
        var countClickComment = 0
        binding.imgLike.setOnClickListener {
            icLikeVideoChange()
        }
        binding.imgDislike.setOnClickListener {
            icDisLikeVideoChange()
        }
        binding.imgShare.setOnClickListener {
            startActivityShare(videoImg)
        }
        binding.txtComments.setOnClickListener {
            countClickComment+=1
            if (countClickComment % 2 == 0) {
                binding.rvComments.visibility = View.GONE
            } else {
                binding.rvComments.visibility = View.VISIBLE
            }
        }
    }

    private fun setupView() {
        binding.txtNameVideo.text = videoTitle
        binding.txtPublishedAt.text = videoPublished.substring(0, 10)
        binding.txtComments.text = "$commentCount bình luận"
        binding.txtViewCount.text = "$viewCount lượt xem"
    }

    private fun setupAdapter() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvPlaylist.layoutManager = layoutManager
        youtubeVideoAdapter = YoutubeVideoAdapter(
            listItemResponse,
            this,
            onClickItemVideoListener = { _, videoItem ->
                navigationManager.gotoWatchVideoActivity(videoItem)
            })
        binding.rvPlaylist.adapter = youtubeVideoAdapter

        /**Comments*/
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvComments.layoutManager = layoutManager
        commentsAdapter = CommentsAdapter(
            listCommentsResponse as MutableList<com.example.cosplayyoutobe.data.model.comments.Item?>,
            this)
        binding.rvComments.adapter = commentsAdapter
    }

    private fun icLikeVideoChange() {
        isLike = if (isLike) {
            binding.imgLike.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.like_down)
            )
            if (isSaveToRealm) {
                deleteVideoLiked(findFirst(videoID))
            }
            false
        } else {
            binding.imgLike.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.like_up)
            )
            if (!isSaveToRealm) {
                saveVideoLikedToRealm()
            }
            true
        }
    }

    private fun icDisLikeVideoChange() {
        isDisLike = if (isDisLike) {
            binding.imgDislike.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.dislike_down)
            )
            false
        } else {
            binding.imgDislike.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.dislike_up)
            )
            true
        }
    }

    override fun onResume() {
        super.onResume()
        getDataFromBundle()
    }

    private fun getDataFromBundle() {
        videoID = intent.getStringExtra(VIDEO_ITEM).toString()
        videoTitle = intent.getStringExtra(VIDEO_NAME_ITEM).toString()
        videoPublished = intent.getStringExtra(VIDEO_PUBLISHED).toString()
        videoImg = intent.getStringExtra(VIDEO_IMAGE).toString()
        viewCount = intent.getStringExtra(VIEW_COUNT).toString()
        commentCount = intent.getStringExtra(COMMENT_COUNT).toString()

        for (item in getVideoLiked()) {
            if (item.videoId == videoID) {
                isSaveToRealm = true
                binding.imgLike.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.like_up)
                )
                return
            } else {
                isSaveToRealm = false
                binding.imgLike.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.like_down)
                )
            }
        }
    }

    private fun setupWatchVideo() {
        youtubePlayer = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(videoID)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("TAG", "onInitializationFailure: p0: ${p0.toString()} \np1: ${p1?.name}")
            }
        }
    }

    private fun findFirst(videoId: String): VideoLiked {
        return realm?.where(VideoLiked::class.java)?.equalTo("videoId", videoId)?.findFirst() as VideoLiked
    }

    private fun deleteVideoLiked(videoLiked: VideoLiked) {
        realm?.beginTransaction()
        videoLiked.deleteFromRealm()
        realm?.commitTransaction()
        realm?.close()
    }

    private fun getVideoLiked(): List<VideoLiked> {
        return realm?.where(VideoLiked::class.java)
            ?.sort("createTime", Sort.DESCENDING)
            ?.limit(50)
            ?.findAllAsync() as RealmResults<VideoLiked>
    }

    private fun saveVideoLikedToRealm() {
        val videoLiked = VideoLiked()
        videoLiked.videoId = videoID
        videoLiked.videoTitle = videoTitle
        videoLiked.videoImg = videoImg
        videoLiked.createTime = System.currentTimeMillis()
        videoLiked.videoPublished = videoPublished
        videoLiked.viewCount = viewCount
        videoLiked.commentCount = commentCount
        realm?.executeTransaction { realm ->
            realm.copyToRealm(videoLiked)
        }
    }
}