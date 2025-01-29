package com.imasha.hydrateme.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.api.ApiClient
import com.imasha.hydrateme.api.ApiService
import com.imasha.hydrateme.api.ApiSource
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.firebase.FirebaseSource
import com.imasha.hydrateme.room.AppDatabase
import com.imasha.hydrateme.room.RecordDao
import com.imasha.hydrateme.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseSource(firebaseAuth: FirebaseAuth): FirebaseSource =
        FirebaseSource(firebaseAuth)

    @Provides
    fun provideApiService(): ApiService = ApiClient.retrofit.create(ApiService::class.java)

    @Provides
    fun provideApiSource(apiService: ApiService): ApiSource = ApiSource(apiService)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideRecordDao(appDatabase: AppDatabase): RecordDao {
        return appDatabase.recordDao()
    }

    @Provides
    fun provideAppRepository(
        firebaseSource: FirebaseSource,
        apiSource: ApiSource,
        userDao: UserDao
    ): AppRepository = AppRepository(firebaseSource, apiSource, userDao)
}
