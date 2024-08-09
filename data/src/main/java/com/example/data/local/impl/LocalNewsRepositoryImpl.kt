package com.example.data.local.impl

import com.example.data.local.dao.NewsDao
import com.example.data.local.repository.LocalNewsRepository
import com.example.data.model.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalNewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : LocalNewsRepository {

    override fun getNewsList(): Flow<List<NewsEntity>> {
        return newsDao.getNews()
    }

    override suspend fun upsertNewsList(newsList: List<NewsEntity>) {
        newsDao.upsert(newsList)
    }
}