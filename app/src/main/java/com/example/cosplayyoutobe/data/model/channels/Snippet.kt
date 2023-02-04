package com.example.cosplayyoutobe.data.model.channels


import com.google.gson.annotations.SerializedName

data class Snippet(
    @SerializedName("customUrl")
    val customUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("localized")
    val localized: Localized,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String
)