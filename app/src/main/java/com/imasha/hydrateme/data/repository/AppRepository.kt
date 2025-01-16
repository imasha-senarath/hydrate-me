package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.firebase.FirebaseSource
import com.imasha.hydrateme.utils.AppConstants.DRINKS_DOC
import com.imasha.hydrateme.utils.AppConstants.USERS_DOC
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import javax.inject.Inject

class AppRepository @Inject constructor (private val firebaseSource: FirebaseSource) {

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
        return firebaseSource.getData(USERS_DOC, getCurrentUserId().toString(), User::class.java)
    }

    suspend fun saveProfile(user: User): Boolean {
        val userMap = mapOf(
            "name" to user.name,
            "weight" to user.weight,
            "gender" to user.gender,
            "wakeUpTime" to user.wakeUpTime,
            "bedTime" to user.bedTime,
        )

        return firebaseSource.saveData(USERS_DOC, getCurrentUserId().toString(), userMap)
    }

    suspend fun saveDrink(dataMap: Map<String, Any>): Boolean {
        return firebaseSource.saveData(DRINKS_DOC, "", dataMap)
    }

    suspend fun getRecords(): List<Record> {
        return firebaseSource.getDataList(
            DRINKS_DOC, Record::class.java, mapOf("user" to getCurrentUserId().toString())
        )
    }

    suspend fun getTodayRecords(): List<Record> {
        return firebaseSource.getDataList(
            DRINKS_DOC,
            Record::class.java,
            mapOf("user" to getCurrentUserId().toString(), "date" to getCurrentDate(DD_MM_YYYY))
        )
    }

    suspend fun deleteRecord(id: String): Boolean {
        return firebaseSource.deleteData(DRINKS_DOC, id)
    }

    fun logout(): Boolean {
        return firebaseSource.logout()
    }
}