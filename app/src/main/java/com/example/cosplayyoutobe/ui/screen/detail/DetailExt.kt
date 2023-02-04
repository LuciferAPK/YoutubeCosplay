package com.example.cosplayyoutobe.ui.screen.detail

import android.content.Intent

fun WatchVideoActivity.startActivityShare(link: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}