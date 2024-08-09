package com.example.feature.screen.news_list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object NewsListRoute

fun NavGraphBuilder.newsList() {
    composable<NewsListRoute> {
        NewsListNavigation()
    }
}