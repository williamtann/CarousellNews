package com.example.feature.screen.news_list

import com.example.feature.component.sort.SortType

sealed interface NewsListEvent {

    data object ObserveNewsData : NewsListEvent
    data object LoadNews : NewsListEvent
    data class SortNews(val sortType: SortType) : NewsListEvent
}


sealed interface NewsListUiEvent {

    data object ShowError : NewsListUiEvent
}
