package com.example.domain.remote.usecase

import com.example.domain.model.NewsUiModel

interface RemoteGetNewsListUseCase {

    suspend fun execute(): List<NewsUiModel>
}