package com.example.data.di

import com.example.data.remote.service.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val BASE_URL = "baseUrl"

    @Named(BASE_URL)
    @Provides
    @Singleton
    fun baseUrl(): String = "https://storage.googleapis.com/"

    @Provides
    @Singleton
    fun provideNewsService(@Named(BASE_URL) baseUrl: String): NewsService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }
}