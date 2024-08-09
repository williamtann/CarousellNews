package com.example.domain.local.impl

import com.example.data.local.repository.LocalNewsRepository
import com.example.data.model.entity.NewsEntity
import com.example.domain.mapper.toUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalGetNewsListUseCaseImplTest {

    @MockK
    private lateinit var localNewsRepository: LocalNewsRepository

    private lateinit var localGetNewsListUseCaseImpl: LocalGetNewsListUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localGetNewsListUseCaseImpl = LocalGetNewsListUseCaseImpl(localNewsRepository)
    }

    @Test
    fun `when executed should get news entity list with mapped to ui model`() = runTest {
        // Given
        val mockNewsEntityList = listOf(mockNewsEntityModel())
        coEvery { localNewsRepository.getNewsList() } returns flowOf(mockNewsEntityList)

        // When
        val result = localGetNewsListUseCaseImpl.execute()

        // Then
        coVerify {
            localGetNewsListUseCaseImpl.execute()
        }
        result.collect { uiModelList ->
            assertEquals(uiModelList, mockNewsEntityList.map { it.toUiModel() })
        }
    }

    private fun mockNewsEntityModel() = NewsEntity(
        id = "id",
        title = "title",
        description = "description",
        bannerUrl = "bannerUrl",
        timeCreated = 123L,
        rank = 0
    )
}