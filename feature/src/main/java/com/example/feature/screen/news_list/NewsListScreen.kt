@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.feature.screen.news_list

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.core.theme.BaseColorScheme
import com.example.core.theme.BaseTypography
import com.example.feature.R
import com.example.feature.component.NewsItem
import com.example.feature.component.sort.SortButton
import com.example.feature.screen.news_list.NewsListEvent.LoadNews
import com.example.feature.screen.news_list.NewsListEvent.ObserveNewsData
import com.example.feature.screen.news_list.NewsListEvent.SortNews
import com.example.feature.screen.news_list.NewsListUiEvent.ShowError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NewsListNavigation(
    viewModel: NewsListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycle).collectLatest {
            if (it == ShowError) {
                scope.launch {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = context.getString(R.string.failed_to_load_news_error),
                        actionLabel = context.getString(R.string.retry_button),
                        duration = SnackbarDuration.Indefinite
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(LoadNews)
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.onEvent(ObserveNewsData)
        viewModel.onEvent(LoadNews)
    }

    NewsListScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        context = context,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun NewsListScreen(
    uiState: NewsListUiState,
    onEvent: (event: NewsListEvent) -> Unit,
    context: Context,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(BaseColorScheme.primary)
                    .padding(start = 48.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.carousell_news),
                    style = BaseTypography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                )
                SortButton(
                    selectedSort = uiState.sortType,
                    onSortSelected = { sortType ->
                        onEvent(SortNews(sortType))
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(snackbarData = snackbarData)
            }
        }
    ) { scaffoldPadding ->
        val currentTime = System.currentTimeMillis() / 1000
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .semantics { testTagsAsResourceId = true }
                        .testTag("loading view")
                        .fillMaxWidth()
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.newsList,
                    key = { it.id }
                ) { item ->
                    NewsItem(
                        modifier = Modifier
                            .semantics { testTagsAsResourceId = true }
                            .testTag("news item")
                            .fillMaxWidth(),
                        item = item,
                        context = context,
                        currentTime = currentTime
                    )
                }
            }
        }
    }
}