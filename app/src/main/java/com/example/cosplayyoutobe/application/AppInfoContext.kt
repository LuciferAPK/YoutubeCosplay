package com.example.cosplayyoutobe.application

class AppInfoContext {
    var appId: String? = null
    var appVersion: String? = null
    var campaignId: String? = null
    var searchHistorySize = 5
    var minRam : Float = 1.7F

    /** Param point by action hashtag*/
    var clickItemInListPoint: Int = 200
    var clickDownloadPoint: Int = 200
    var clickFavoritesPoint: Int = 200
    var clickSettingPoint: Int = 250

    var bestHashtagForUser : String? = null
    var isDisplaySpecialData : Boolean = false

    /** Param point by position hashtag*/
    var hashtagInPosPoint: List<Double> = listOf(1.0, 1.0, 2.31127, 1.76267, 1.50439, 1.35803, 1.26537, 1.20223, 1.15689, 1.12304)
}