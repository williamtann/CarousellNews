package com.example.domain.remote.impl

import com.example.data.remote.repository.RemoteNewsRepository
import com.example.domain.mapper.toUiModel
import com.example.domain.model.NewsUiModel
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import javax.inject.Inject

class RemoteGetNewsListUseCaseImpl @Inject constructor(
    private val remoteNewsRepository: RemoteNewsRepository
) : RemoteGetNewsListUseCase {

    override suspend fun execute(): List<NewsUiModel> {
        return remoteNewsRepository.getNewsList().map { it.toUiModel() }
    }
}