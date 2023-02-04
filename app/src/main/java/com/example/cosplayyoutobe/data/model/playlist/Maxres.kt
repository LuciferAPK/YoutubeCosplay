package com.example.cosplayyoutobe.data.model.playlist


import com.google.gson.annotations.SerializedName

data class Maxres(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)