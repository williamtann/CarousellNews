package com.example.data.model.response

import com.google.gson.annotations.SerializedName

data class NewsApiModel(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("banner_url") val bannerUrl: String,
    @SerializedName("time_created") val timeCreated: Long,
    val rank: Int
)