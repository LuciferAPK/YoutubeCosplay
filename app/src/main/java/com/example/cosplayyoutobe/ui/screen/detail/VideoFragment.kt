package com.example.cosplayyoutobe.ui.screen.detail

import android.os.Bundle
import android.util.Log
import com.example.cosplayyoutobe.BuildConfig
import com.example.cosplayyoutobe.databinding.FragmentVideoBinding
import com.example.cosplayyoutobe.navigation.KeyDataIntent
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class VideoFragment : YouTubeBaseActivity() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var youtubePlayer: YouTubePlayer.OnInitializedListener
    private var videoID: String = ""
//    private var videoTitle: String = ""
//    private var videoPublished: String = ""
//    private var videoImg: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWatchVideo()
        binding.youtubePlayer.initialize(BuildConfig.API_KEY, youtubePlayer)
        getDataFromBundle()
    }

    private fun getDataFromBundle() {
        videoID = intent.getStringExtra(KeyDataIntent.VIDEO_ITEM).toString()
//        videoTitle = intent.getStringExtra(KeyDataIntent.VIDEO_NAME_ITEM).toString()
//        videoPublished = intent.getStringExtra(KeyDataIntent.VIDEO_PUBLISHED).toString()
//        videoImg = intent.getStringExtra(KeyDataIntent.VIDEO_IMAGE).toString()
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
}