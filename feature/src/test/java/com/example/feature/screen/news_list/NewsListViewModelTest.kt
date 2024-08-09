package com.example.feature.screen.news_list

import app.cash.turbine.test
import com.example.domain.local.usecase.LocalGetNewsListUseCase
import com.example.domain.model.NewsUiModel
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import com.example.feature.component.sort.SortType
import com.example.feature.rules.CoroutineMainDispatcherRule
import com.example.feature.screen.news_list.NewsListEvent.LoadNews
import com.example.feature.screen.news_list.NewsListEvent.ObserveNewsData
import com.example.feature.screen.news_list.NewsListEvent.SortNews
import com.example.feature.screen.news_list.NewsListUiEvent.ShowError
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
import org.junit.Rule
import org.junit.Test

class NewsListViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineMainDispatcherRule()

    @MockK
    private lateinit var localGetNewsListUseCase: LocalGetNewsListUseCase

    @MockK
    private lateinit var remoteGetNewsListUseCase: RemoteGetNewsListUseCase

    private lateinit var viewModel: NewsListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = NewsListViewModel(
            localGetNewsListUseCase = localGetNewsListUseCase,
            remoteGetNewsListUseCase = remoteGetNewsListUseCase
        )
    }

    @Test
    fun `when observe news data should get data from database`() =
        runTest {
            // Given
            coEvery { localGetNewsListUseCase.execute() } returns flowOf(emptyList())

            // When
            viewModel.onEvent(ObserveNewsData)

            // Then
            coVerify {
                localGetNewsListUseCase.execute()
            }
        }

    @Test
    fun `when load news should fetch news list and update loading states`() =
        runTest {
            viewModel.uiState.test {
                // Given
                coEvery { remoteGetNewsListUseCase.execute() } just runs

                // When
                viewModel.onEvent(LoadNews)

                // Then
                assertEquals(false, expectMostRecentItem().isLoading)
                coVerify {
                    remoteGetNewsListUseCase.execute()
                }
            }
        }

    @Test
    fun `when load news failed should emit show error`() =
        runTest {
            viewModel.uiEvent.test {
                // Given
                coEvery { remoteGetNewsListUseCase.execute() } throws Exception()

                // When
                viewModel.onEvent(LoadNews)

                // Then
                assertEquals(ShowError, expectMostRecentItem())
                coVerify {
                    remoteGetNewsListUseCase.execute()
                }
            }
        }

    @Test
    fun `when load news failed should stop loading`() =
        runTest {
            viewModel.uiState.test {
                // Given
                coEvery { remoteGetNewsListUseCase.execute() } throws Exception()

                // When
                viewModel.onEvent(LoadNews)

                // Then
                assertEquals(false, expectMostRecentItem().isLoading)
                coVerify {
                    remoteGetNewsListUseCase.execute()
                }
            }
        }

    @Test
    fun `when sort news should sort update the sort type state`() =
        runTest {
            viewModel.uiState.test {
                // Given
                val sortType = SortType.Popular

                // When
                viewModel.onEvent(SortNews(sortType))

                // Then
                assertEquals(sortType, expectMostRecentItem().sortType)
            }
        }

    @Test
    fun `when sort news should sort the fetched news list`() =
        runTest {
            viewModel.uiState.test {
                // Given
                val sortType = SortType.Popular
                val firstNewsUiModel = mockNewsUiModel().copy(timeCreated = 1L, rank = 2)
                val secondNewsUiModel = mockNewsUiModel().copy(timeCreated = 2L, rank = 1)
                coEvery { localGetNewsListUseCase.execute() } returns flowOf(
                    listOf(firstNewsUiModel, secondNewsUiModel)
                )

                // When
                viewModel.onEvent(ObserveNewsData)
                viewModel.onEvent(SortNews(sortType))

                // Then
                val latestNewsList = expectMostRecentItem().newsList
                assertEquals(secondNewsUiModel, latestNewsList[0])
                assertEquals(firstNewsUiModel, latestNewsList[1])
            }
        }

    private fun mockNewsUiModel() = NewsUiModel(
        id = "id",
        title = "title",
        description = "description",
        bannerUrl = "bannerUrl",
        timeCreated = 123L,
        rank = 0
    )
}

