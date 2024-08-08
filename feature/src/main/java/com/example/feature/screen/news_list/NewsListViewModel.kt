package com.example.feature.screen.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.safeLaunch
import com.example.domain.model.NewsUiModel
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class NewsListUiState(
    val newsList: List<NewsUiModel> = emptyList()
)

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val remoteGetNewsListUseCase: RemoteGetNewsListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState

    fun getNewsList() {
        viewModelScope.safeLaunch(
            block = {
                val newsList = remoteGetNewsListUseCase.execute()
                _uiState.update {
                    it.copy(newsList = newsList)
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}