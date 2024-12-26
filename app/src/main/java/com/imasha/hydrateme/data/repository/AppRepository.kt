package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.source.FirebaseSource

class AppRepository(private val firebaseSource: FirebaseSource) {

    suspend fun userAuthentication(): Boolean {
        return firebaseSource.userAuthentication()
    }

    suspend fun login(email: String, password: String): Boolean {
        return firebaseSource.login(email, password)
    }
}