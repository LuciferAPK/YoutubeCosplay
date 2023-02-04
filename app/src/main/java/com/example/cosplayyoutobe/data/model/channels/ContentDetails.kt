package com.example.cosplayyoutobe.data.model.channels


import com.google.gson.annotations.SerializedName

data class ContentDetails(
    @SerializedName("relatedPlaylists")
    val relatedPlaylists: RelatedPlaylists
)