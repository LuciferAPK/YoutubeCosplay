package com.example.cosplayyoutobe.application

import android.util.Log
import java.util.*


class NetworkContext {

    private val TAG = NetworkContext::class.simpleName

    var isNetworkConnected: Boolean = true
    var connectTimeout: Long = 30L //seconds
    var readTimeout: Long = 30L //seconds
    var retry: Int = 4

    //
    var currentURL = ""
    //update hashmap when load configure from firebase remote config
    var regionConfigMap = hashMapOf<String, String>()

    var countryKey = Locale.getDefault().country

    var apiList = mutableListOf<String>()

    init {

    }

    fun assignCountry(country : String = this.countryKey) {
        this.countryKey = country
        Log.i("startup", "CountryCodeInitializer::this.countryKey = $countryKey")
    }

    private fun asignApiUrl(apis : String?) {
        checkNotNull(apis) {Log.i("NetworkConfig","apis must not be null.")}
        //
        Log.i("NetworkConfig", "renewApiSet::Firebase remote config: $apis")
        val apiArray = apis.split(",")
        apiList.clear()
        Log.i("NetworkConfig", "apiSet.size = ${apiList.size}")
        apiList.addAll(apiArray)
        Log.i("NetworkConfig", "apiSet.size = ${apiList.size}")
    }

    fun setCurrentUrl(url: String) {
        currentURL = url
        Log.d("Walla", "currentURL=$currentURL")
    }

    fun setFailedOfUrl() {
        if (apiList.contains(currentURL) && apiList.size > 1) {
            Log.d("Walla", "remove currentURL=$currentURL")
            //remove the first
            apiList.remove(currentURL)
            //insert the last
            //apiList.add(apiList.size, currentURL)
        }
    }
}