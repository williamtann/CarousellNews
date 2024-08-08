package com.example.domain.mapper

import com.example.data.model.response.NewsApiModel
import com.example.domain.model.NewsUiModel

fun NewsApiModel.toUiModel() = NewsUiModel(
    id = this.id,
    title = this.title,
    description = this.description,
    bannerUrl = this.bannerUrl,
    timeCreated = this.timeCreated,
    rank = this.rank
)