package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.dao.NewsDao
import com.example.data.remote.service.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: AppDatabase): NewsDao = database.newsDao()
}