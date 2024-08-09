package com.example.domain.local.usecase

import com.example.domain.model.NewsUiModel
import kotlinx.coroutines.flow.Flow

interface LocalGetNewsListUseCase {

    fun execute(): Flow<List<NewsUiModel>>
}