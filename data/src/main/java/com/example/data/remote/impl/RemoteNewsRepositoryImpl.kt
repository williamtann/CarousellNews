package com.example.data.remote.impl

import com.example.data.model.response.NewsApiModel
import com.example.data.remote.repository.RemoteNewsRepository
import com.example.data.remote.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteNewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : RemoteNewsRepository {

    override suspend fun getNewsList(): List<NewsApiModel> {
        return withContext(Dispatchers.IO) {
            newsService.getNewsList()
        }
    }
}