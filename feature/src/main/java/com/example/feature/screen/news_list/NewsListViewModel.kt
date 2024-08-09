package com.example.feature.screen.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.safeLaunch
import com.example.domain.local.usecase.LocalGetNewsListUseCase
import com.example.domain.model.NewsUiModel
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import com.example.feature.component.sort.SortType
import com.example.feature.screen.news_list.NewsListEvent.LoadNews
import com.example.feature.screen.news_list.NewsListEvent.ObserveNewsData
import com.example.feature.screen.news_list.NewsListEvent.SortNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class NewsListUiState(
    val newsList: List<NewsUiModel> = emptyList(),
    val sortType: SortType = SortType.recent
)

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val localGetNewsListUseCase: LocalGetNewsListUseCase,
    private val remoteGetNewsListUseCase: RemoteGetNewsListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState

    fun onEvent(event: NewsListEvent) {
        when (event) {
            is ObserveNewsData -> observeNewsData()
            is LoadNews -> getNewsList()
            is SortNews -> sortNewsData(event.sortType)
        }
    }

    private fun observeNewsData() {
        viewModelScope.safeLaunch(
            block = {
                localGetNewsListUseCase.execute().collect { newsList ->
                    _uiState.update { state ->
                        state.copy(
                            newsList = sortNews(
                                newsList = newsList,
                                sortType = state.sortType
                            )
                        )
                    }
                }
            }
        )
    }

    private fun getNewsList() {
        viewModelScope.safeLaunch(
            block = {
                remoteGetNewsListUseCase.execute()
            }
        )
    }

    private fun sortNewsData(sortType: SortType) {
        _uiState.update {
            it.copy(
                sortType = sortType,
                newsList = sortNews(
                    newsList = it.newsList,
                    sortType = sortType
                )
            )
        }
    }

    private fun sortNews(newsList: List<NewsUiModel>, sortType: SortType): List<NewsUiModel> {
        return newsList.sortedBy { news ->
            if (sortType == SortType.recent) {
                news.timeCreated
            } else {
                news.rank.toLong()
            }
        }
    }
}