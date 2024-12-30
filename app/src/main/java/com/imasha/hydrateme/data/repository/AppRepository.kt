package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.source.FirebaseSource

class AppRepository(private val firebaseSource: FirebaseSource) {

    suspend fun userAuthentication(): Boolean {
        return firebaseSource.userAuthentication()
    }

    suspend fun login(user: User): Boolean {
        return firebaseSource.login(user)
    }

    suspend fun signUp(user: User): Boolean {
        return firebaseSource.signUp(user)
    }

    fun logout(): Boolean {
        return firebaseSource.logout()
    }
}