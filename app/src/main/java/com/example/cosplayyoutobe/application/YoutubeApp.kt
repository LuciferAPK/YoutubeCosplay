package com.example.cosplayyoutobe.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class YoutubeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}