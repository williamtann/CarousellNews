package com.example.feature.screen.news_list

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.example.domain.model.NewsUiModel
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun onLoading_shouldShowLoadingIndicator() {
        composeRule.run {
            // Given
            setScreenContent(
                uiState = NewsListUiState(
                    isLoading = true
                ),
                context = context
            )

            // Then
            onNodeWithTag("loading view").assertIsDisplayed()
        }
    }

    @Test
    fun onNotLoading_shouldNotShowLoadingIndicator() {
        composeRule.run {
            // Given
            setScreenContent(
                uiState = NewsListUiState(
                    isLoading = false
                ),
                context = context
            )

            // Then
            onNodeWithTag("loading view").assertDoesNotExist()
        }
    }

    @Test
    fun onNewsItemLoaded_shouldShowNewsItem() {
        composeRule.run {
            // Given
            setScreenContent(
                uiState = NewsListUiState(
                    newsList = listOf(
                        NewsUiModel(
                            id = "id",
                            title = "title",
                            description = "description",
                            bannerUrl = "bannerUrl",
                            timeCreated = 0L,
                            rank = 0
                        )
                    )
                ),
                context = context
            )

            // Then
            onNodeWithTag("news item").assertIsDisplayed()
        }
    }

    @Test
    fun onNewsItemNotLoaded_shouldNotShowNewsItem() {
        composeRule.run {
            // Given
            setScreenContent(
                uiState = NewsListUiState(
                    newsList = emptyList()
                ),
                context = context
            )

            // Then
            onNodeWithTag("news item").assertDoesNotExist()
        }
    }

    private fun ComposeContentTestRule.setScreenContent(
        uiState: NewsListUiState = NewsListUiState(),
        onEvent: (event: NewsListEvent) -> Unit = {},
        context: Context,
        snackbarHostState: SnackbarHostState = SnackbarHostState()
    ) {
        setContent {
            NewsListScreen(
                uiState = uiState,
                onEvent = onEvent,
                context = context,
                snackbarHostState = snackbarHostState
            )
        }
    }
}
