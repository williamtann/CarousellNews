package com.example.domain.model

data class NewsUiModel(
    val id: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val timeCreated: Long,
    val rank: Int
)