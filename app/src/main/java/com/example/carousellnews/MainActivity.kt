package com.example.carousellnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.carousellnews.ui.theme.CarousellNewsTheme
import com.example.feature.screen.news_list.NewsListNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarousellNewsTheme {
                NewsListNavigation()
            }
        }
    }
}