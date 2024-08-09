package com.example.feature.screen.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.theme.BaseColorScheme
import com.example.core.theme.BaseTypography
import com.example.feature.R
import com.example.feature.component.NewsItem
import com.example.feature.component.sort.SortButton
import com.example.feature.screen.news_list.NewsListEvent.LoadNews
import com.example.feature.screen.news_list.NewsListEvent.ObserveNewsData
import com.example.feature.screen.news_list.NewsListEvent.SortNews

@Composable
fun NewsListNavigation(
    viewModel: NewsListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ObserveNewsData)
        viewModel.onEvent(LoadNews)
    }

    NewsListScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun NewsListScreen(
    uiState: NewsListUiState,
    onEvent: (event: NewsListEvent) -> Unit
) {
    val context = LocalContext.current

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
                    onSortSelected = { sortType ->
                        onEvent(SortNews(sortType))
                    }
                )
            }
        }
    ) { scaffoldPadding ->
        val currentTime = System.currentTimeMillis() / 1000
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = uiState.newsList,
                key = { it.id }
            ) { item ->
                NewsItem(
                    item = item,
                    context = context,
                    currentTime = currentTime
                )
            }
        }
    }
}