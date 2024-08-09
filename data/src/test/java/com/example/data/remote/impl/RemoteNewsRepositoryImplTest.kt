package com.example.data.remote.impl

import com.example.data.model.response.NewsApiModel
import com.example.data.remote.service.NewsService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteNewsRepositoryImplTest {

    @MockK
    private lateinit var newsService: NewsService

    private lateinit var remoteRepository: RemoteNewsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteRepository = RemoteNewsRepositoryImpl(newsService)
    }

    @Test
    fun `when get news success then return expected response payload`() = runTest {
        // Given
        val mockNewsApiModelList = listOf(mockNewsApiModel())
        coEvery { newsService.getNewsList() } returns mockNewsApiModelList

        // When
        val result = remoteRepository.getNewsList()

        // Then
        assertEquals(result, mockNewsApiModelList)
    }

    private fun mockNewsApiModel() = NewsApiModel(
        id = "id",
        title = "title",
        description = "description",
        bannerUrl = "bannerUrl",
        timeCreated = 123L,
        rank = 0
    )
}