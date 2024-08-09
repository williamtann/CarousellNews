package com.example.domain.di

import com.example.domain.local.impl.LocalGetNewsListUseCaseImpl
import com.example.domain.local.usecase.LocalGetNewsListUseCase
import com.example.domain.remote.impl.RemoteGetNewsListUseCaseImpl
import com.example.domain.remote.usecase.RemoteGetNewsListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    @Singleton
    abstract fun bindsLocalGetNewsListUseCase(impl: LocalGetNewsListUseCaseImpl): LocalGetNewsListUseCase

    @Binds
    @Singleton
    abstract fun bindsRemoteGetNewsListUseCase(impl: RemoteGetNewsListUseCaseImpl): RemoteGetNewsListUseCase
}