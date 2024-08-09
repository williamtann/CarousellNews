package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.model.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): Flow<List<NewsEntity>>

    @Upsert
    suspend fun upsert(newsList: List<NewsEntity>)

    @Query("DELETE FROM news WHERE id = :newsId")
    suspend fun deleteById(newsId: Long): Int
}