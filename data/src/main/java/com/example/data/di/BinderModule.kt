package com.example.data.di

import com.example.data.remote.impl.RemoteNewsRepositoryImpl
import com.example.data.remote.repository.RemoteNewsRepository
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
    abstract fun bindsRemoteNewsRepository(impl: RemoteNewsRepositoryImpl): RemoteNewsRepository
}