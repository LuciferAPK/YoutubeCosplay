package com.example.cosplayyoutobe.data.model.comments


import com.google.gson.annotations.SerializedName

data class TopLevelComment(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: SnippetX
)