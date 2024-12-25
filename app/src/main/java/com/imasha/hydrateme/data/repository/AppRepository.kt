package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.source.FirebaseSource

class AppRepository(private val firebaseSource: FirebaseSource) {

    suspend fun login(email: String, password: String): Boolean {
        return firebaseSource.login(email, password)
    }

    /*fun login(email: String, password: String, callback: (Result<Boolean>) -> Unit) {
        firebaseSource.login(email, password) { result ->
            result.onSuccess {
                callback(Result.success(true))
            }.onFailure {
                callback(Result.failure(it))
            }
        }
    }*/
}