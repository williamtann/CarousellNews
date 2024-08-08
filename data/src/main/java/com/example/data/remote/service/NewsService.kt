package com.example.data.remote.service

import com.example.data.model.response.NewsApiModel
import retrofit2.http.GET

interface NewsService {

    @GET("carousell-interview-assets/android/carousell_news.json")
    suspend fun getNewsList(): List<NewsApiModel>
}