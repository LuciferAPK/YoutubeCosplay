package com.example.cosplayyoutobe.data.model.video


import com.google.gson.annotations.SerializedName

data class RegionRestriction(
    @SerializedName("allowed")
    val allowed: List<String>,
    @SerializedName("blocked")
    val blocked: List<String>
)