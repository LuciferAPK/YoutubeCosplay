package com.example.cosplayyoutobe.data.model_realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class VideoLiked: RealmObject() {
    @PrimaryKey
    var videoId: String? = null
    var createTime: Long = 0
    var videoTitle: String? = null
    var videoImg: String? = null
    var videoPublished: String? = null
    var viewCount: String? = null
    var commentCount: String? = null
}