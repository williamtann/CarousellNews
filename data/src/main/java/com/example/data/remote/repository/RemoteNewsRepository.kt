package com.example.data.remote.repository

import com.example.data.model.response.NewsApiModel

interface RemoteNewsRepository {

    suspend fun getNewsList(): List<NewsApiModel>
}