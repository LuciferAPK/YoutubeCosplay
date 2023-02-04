package com.example.cosplayyoutobe.ext

import android.util.DisplayMetrics

lateinit var displayMetrics: DisplayMetrics
val widthScreen: Int get() = displayMetrics.widthPixels
val heightScreen: Int get() = displayMetrics.heightPixels