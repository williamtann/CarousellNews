package com.example.data.local.repository

import com.example.data.model.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface LocalNewsRepository {

    fun getNewsList(): Flow<List<NewsEntity>>
    suspend fun upsertNewsList(newsList: List<NewsEntity>)
}
