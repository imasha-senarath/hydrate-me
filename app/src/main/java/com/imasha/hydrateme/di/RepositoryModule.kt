package com.imasha.hydrateme.di

import com.imasha.hydrateme.data.repository.AppRepositoryImpl
import com.imasha.hydrateme.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository
}