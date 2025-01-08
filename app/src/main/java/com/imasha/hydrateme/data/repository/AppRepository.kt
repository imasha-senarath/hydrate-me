package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.utils.AppConstants.DRINKS
import com.imasha.hydrateme.utils.AppConstants.USERS
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate

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

    suspend fun getProfile(): User {
        return firebaseSource.getData(USERS, getCurrentUserId().toString(), User::class.java)
    }

    suspend fun saveProfile(dataMap: Map<String, Any>): Boolean {
        return firebaseSource.saveData(USERS, getCurrentUserId().toString(), dataMap)
    }

    suspend fun saveDrink(dataMap: Map<String, Any>): Boolean {
        return firebaseSource.saveData(DRINKS, "", dataMap)
    }

    suspend fun getRecords(): List<Record> {
        return firebaseSource.getDataList(
            DRINKS, Record::class.java, mapOf("user" to getCurrentUserId().toString())
        )
    }

    suspend fun getTodayRecords(): List<Record> {
        return firebaseSource.getDataList(
            DRINKS,
            Record::class.java,
            mapOf("user" to getCurrentUserId().toString(), "date" to getCurrentDate(DD_MM_YYYY))
        )
    }

    fun logout(): Boolean {
        return firebaseSource.logout()
    }
}