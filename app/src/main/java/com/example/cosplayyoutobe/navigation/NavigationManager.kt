package com.example.cosplayyoutobe.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.data.model.video.Item
import com.example.cosplayyoutobe.data.model_realm.VideoHistory
import com.example.cosplayyoutobe.data.model_realm.VideoLiked
import com.example.cosplayyoutobe.navigation.KeyDataIntent.COMMENT_COUNT
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_IMAGE
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_ITEM
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_NAME_ITEM
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIDEO_PUBLISHED
import com.example.cosplayyoutobe.navigation.KeyDataIntent.VIEW_COUNT
import com.example.cosplayyoutobe.ui.screen.detail.WatchVideoActivity
import com.example.cosplayyoutobe.ui.screen.lib.fragment.HistoryFragment
import com.example.cosplayyoutobe.ui.screen.lib.fragment.VideoLikedFragment
import com.example.cosplayyoutobe.ui.screen.login.LoginActivity
import com.example.cosplayyoutobe.ui.screen.main.MainActivity
import javax.inject.Singleton

@Singleton
class NavigationManager(private val context: Context) {

    fun gotoMainActivity(){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun gotoLoginActivity(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun gotoWatchVideoActivity(item: Item){
        val intent = Intent(context, WatchVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(VIDEO_ITEM, item.id)
        intent.putExtra(VIDEO_NAME_ITEM, item.snippet.title)
        intent.putExtra(VIDEO_PUBLISHED, item.snippet.publishedAt)
        intent.putExtra(VIDEO_IMAGE, item.snippet.thumbnails.standard.url)
        intent.putExtra(VIEW_COUNT, item.statistics.viewCount)
        intent.putExtra(COMMENT_COUNT, item.statistics.commentCount)
        context.startActivity(intent)
    }

    fun gotoWatchVideoActivityFromVideoSearch(item: com.example.cosplayyoutobe.data.model.search.Item){
        val intent = Intent(context, WatchVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(VIDEO_ITEM, item.id.videoId)
        intent.putExtra(VIDEO_NAME_ITEM, item.snippet.title)
        intent.putExtra(VIDEO_PUBLISHED, item.snippet.publishedAt)
        intent.putExtra(VIDEO_IMAGE, item.snippet.thumbnails.high.url)
        intent.putExtra(VIEW_COUNT, "")
        intent.putExtra(COMMENT_COUNT, "")
        context.startActivity(intent)
    }

    fun gotoWatchVideoActivityFromVideoLiked(video: VideoLiked){
        val intent = Intent(context, WatchVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(VIDEO_ITEM, video.videoId)
        intent.putExtra(VIDEO_NAME_ITEM, video.videoTitle)
        intent.putExtra(VIDEO_PUBLISHED, video.videoPublished)
        intent.putExtra(VIDEO_IMAGE, video.videoImg)
        intent.putExtra(VIEW_COUNT, video.viewCount)
        intent.putExtra(COMMENT_COUNT, video.commentCount)
        context.startActivity(intent)
    }

    fun gotoWatchVideoActivityFromVideoHistory(video: VideoHistory){
        val intent = Intent(context, WatchVideoActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(VIDEO_ITEM, video.videoId)
        intent.putExtra(VIDEO_NAME_ITEM, video.videoTitle)
        intent.putExtra(VIDEO_PUBLISHED, video.videoPublished)
        intent.putExtra(VIDEO_IMAGE, video.videoImg)
        intent.putExtra(VIEW_COUNT, video.viewCount)
        intent.putExtra(COMMENT_COUNT, video.commentCount)
        context.startActivity(intent)
    }

    fun navigationToHistory(parentFragmentManager: FragmentManager) {
        val bundle = Bundle()
        val fragment = HistoryFragment().apply { arguments = bundle }
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.content_lib, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }

    fun navigationToVideoLikedFragment(parentFragmentManager: FragmentManager) {
        val bundle = Bundle()
        val fragment = VideoLikedFragment().apply { arguments = bundle }
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.content_lib, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }
}