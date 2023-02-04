package com.example.cosplayyoutobe.network

const val END_POINT_GET_PLAYLIST_VIDEO = "playlistItems"
const val END_POINT_GET_VIDEO_RANDOM = "videos"
const val END_POINT_GET_COMMENT = "commentThreads"
const val END_POINT_GET_CHANNEL_ID = "channels"
const val END_POINT_GET_VIDEO_SEARCH = "search"

//fun generateUrlRetry(endPoint: String) : UrlsRetry {
//    val urlsRetry: Queue<ApiUrl> = LinkedList()
//    for (url in ApplicationContext.getNetworkContext().apiList) {
//        urlsRetry.add(ApiUrl(url, "${url}$endPoint"))
//    }
//    return UrlsRetry(urlsRetry)
//}