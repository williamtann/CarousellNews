package com.example.data.local.impl

import com.example.data.local.dao.NewsDao
import com.example.data.model.entity.NewsEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalNewsRepositoryImplTest {

    @MockK
    private lateinit var newsDao: NewsDao

    private lateinit var localNewsRepository: LocalNewsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localNewsRepository = LocalNewsRepositoryImpl(newsDao)
    }

    @Test
    fun `when get news list success then return expected entity response`() = runTest {
        // Given
        val mockNewsEntityList: List<NewsEntity> = listOf(
            mockNewsEntity()
        )
        coEvery { newsDao.getNews() } returns flowOf(mockNewsEntityList)

        // When
        val result = localNewsRepository.getNewsList()

        // Then
        result.collect {
            assertEquals(it, mockNewsEntityList)
        }
    }

    @Test
    fun `when upsert news then should invoke upsert from the respective dao`() = runTest {
        // Given
        val mockNewsList = listOf(mockNewsEntity())
        coEvery { newsDao.upsert(mockNewsList) } just runs

        // When
        localNewsRepository.upsertNewsList(mockNewsList)

        // Then
        coVerify {
            newsDao.upsert(mockNewsList)
        }
    }

    private fun mockNewsEntity() = NewsEntity(
        id = "id",
        title = "title",
        description = "description",
        bannerUrl = "bannerUrl",
        timeCreated = 123L,
        rank = 0
    )
}