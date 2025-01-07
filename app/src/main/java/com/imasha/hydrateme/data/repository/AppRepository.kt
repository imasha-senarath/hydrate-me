package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.source.FirebaseSource

class AppRepository(private val firebaseSource: FirebaseSource) {

    suspend fun userAuthentication(): Boolean {
        return firebaseSource.userAuthentication()
    }

    fun getCurrentUserId(): String? {
        return firebaseSource.getCurrentUserId()
    }

    suspend fun login(user: User): Boolean {
        return firebaseSource.login(user)
    }

    suspend fun signUp(user: User): Boolean {
        return firebaseSource.signUp(user)
    }

    suspend fun saveData(
        collection: String,
        document: String,
        dataMap: Map<String, Any>,
    ): Boolean {
        return firebaseSource.saveData(collection, document, dataMap)
    }

    suspend fun <T> getDataList(
        collection: String,
        clazz: Class<T>
    ): List<T> {
        return firebaseSource.getDataList(collection, clazz)
    }

    fun logout(): Boolean {
        return firebaseSource.logout()
    }
}