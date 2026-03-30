package com.imasha.hydrateme.di

import android.content.Context
import com.imasha.hydrateme.data.local.AppDatabase
import com.imasha.hydrateme.data.local.DataStoreManager
import com.imasha.hydrateme.data.remote.api.ApiClient
import com.imasha.hydrateme.data.remote.api.ApiService
import com.imasha.hydrateme.data.remote.api.ApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreManager = DataStoreManager(context)
}
