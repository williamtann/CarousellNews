package com.example.domain.remote.impl

import com.example.data.local.repository.LocalNewsRepository
import com.example.data.model.response.NewsApiModel
import com.example.data.remote.repository.RemoteNewsRepository
import com.example.domain.mapper.toEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteGetNewsListUseCaseImplTest {

    @MockK
    private lateinit var remoteNewsRepository: RemoteNewsRepository

    @MockK
    private lateinit var localNewsRepository: LocalNewsRepository

    private lateinit var remoteGetNewsListUseCaseImpl: RemoteGetNewsListUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteGetNewsListUseCaseImpl = RemoteGetNewsListUseCaseImpl(
            remoteNewsRepository = remoteNewsRepository,
            localNewsRepository = localNewsRepository
        )
    }

    @Test
    fun `when executed should fetch news api model list with mapped to entity, and saved to local db`() = runTest {
        // Given
        val mockNewsApiModelList = listOf(mockNewsApiModel())
        val expectedEntityList = mockNewsApiModelList.map { it.toEntity() }
        coEvery { remoteNewsRepository.getNewsList() } returns mockNewsApiModelList
        coEvery { localNewsRepository.upsertNewsList(any()) } just runs

        // When
        remoteGetNewsListUseCaseImpl.execute()

        // Then
        coVerify {
            remoteNewsRepository.getNewsList()
            localNewsRepository.upsertNewsList(newsList = expectedEntityList)
        }
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