package com.imasha.hydrateme.di

import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.firebase.FirebaseSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseSource(firebaseAuth: FirebaseAuth): FirebaseSource = FirebaseSource(firebaseAuth)

    @Provides
    fun provideAppRepository(firebaseSource: FirebaseSource): AppRepository = AppRepository(firebaseSource)
}
