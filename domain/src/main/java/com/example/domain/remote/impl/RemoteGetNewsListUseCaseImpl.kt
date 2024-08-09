package com.example.domain.remote.impl

import com.example.data.local.repository.LocalNewsRepository
import com.example.data.remote.repository.RemoteNewsRepository
import com.example.domain.mapper.toEntity
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import javax.inject.Inject

class RemoteGetNewsListUseCaseImpl @Inject constructor(
    private val remoteNewsRepository: RemoteNewsRepository,
    private val localNewsRepository: LocalNewsRepository
) : RemoteGetNewsListUseCase {

    override suspend fun execute() {
        val newsList = remoteNewsRepository.getNewsList().map { it.toEntity() }
        localNewsRepository.upsertNewsList(newsList = newsList)
    }
}