package com.example.domain.local.impl

import com.example.data.local.repository.LocalNewsRepository
import com.example.domain.local.usecase.LocalGetNewsListUseCase
import com.example.domain.mapper.toUiModel
import com.example.domain.model.NewsUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalGetNewsListUseCaseImpl @Inject constructor(
    private val localNewsRepository: LocalNewsRepository
) : LocalGetNewsListUseCase {

    override fun execute(): Flow<List<NewsUiModel>> {
        return localNewsRepository.getNewsList().map { list ->
            list.map { model ->
                model.toUiModel()
            }
        }
    }
}