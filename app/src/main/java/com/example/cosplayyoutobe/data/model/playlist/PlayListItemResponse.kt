package com.example.cosplayyoutobe.data.model.playlist

import com.google.gson.annotations.SerializedName

data class PlayListItemResponse(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
)