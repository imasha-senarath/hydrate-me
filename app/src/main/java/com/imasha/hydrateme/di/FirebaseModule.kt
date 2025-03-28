package com.imasha.hydrateme.di

import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.data.remote.firebase.FirebaseSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseSource(firebaseAuth: FirebaseAuth): FirebaseSource {
        return FirebaseSource(firebaseAuth)
    }
}