package com.imasha.hydrateme.di

import com.imasha.hydrateme.data.remote.api.ApiClient
import com.imasha.hydrateme.data.remote.api.ApiService
import com.imasha.hydrateme.data.remote.api.ApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiClient.retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiSource(apiService: ApiService): ApiSource = ApiSource(apiService)
}
