package com.example.carousellnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.feature.screen.news_list.NewsListRoute
import com.example.feature.screen.news_list.newsList

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NewsListRoute
    ) {
        newsList()
    }
}