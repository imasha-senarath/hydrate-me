package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.api.ApiSource
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.firebase.FirebaseSource
import com.imasha.hydrateme.room.UserDao
import com.imasha.hydrateme.utils.AppConstants.DRINKS_DOC
import com.imasha.hydrateme.utils.AppConstants.NOTIFICATIONS_DOC
import com.imasha.hydrateme.utils.AppConstants.USERS_DOC
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val apiSource: ApiSource,
    private val userDao: UserDao
) {

    suspend fun userAuthentication(): Boolean {
        return firebaseSource.userAuthentication()
    }

    fun getCurrentUserId(): String? {
        return firebaseSource.getCurrentUserId()
    }

    suspend fun getFcmToken(): String {
        return firebaseSource.getFcmToken()
    }

    suspend fun login(user: User): Boolean {
        return firebaseSource.login(user)
    }

    suspend fun signUp(user: User): Boolean {
        return firebaseSource.signUp(user)
    }

    suspend fun getProfile(): User {
        val localUser = userDao.getUser(getCurrentUserId().toString())

        if(localUser != null) {
            return localUser;
        }

        return firebaseSource.getData(USERS_DOC, getCurrentUserId().toString(), User::class.java)
    }

    suspend fun saveProfile(user: User): Boolean {
        val userMap = mapOf(
            "name" to user.name,
            "weight" to user.weight,
            "gender" to user.gender,
            "wakeUpTime" to user.wakeUpTime,
            "bedTime" to user.bedTime,
            "goal" to user.goal,
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

    suspend fun getNotifications(): List<Notification> {
        return firebaseSource.getDataList(
            NOTIFICATIONS_DOC,
            Notification::class.java,
            mapOf("user" to getCurrentUserId().toString())
        )
    }

    suspend fun saveNotification(notification: Notification): Boolean {
        val notificationMap = mapOf(
            "title" to notification.title,
            "message" to notification.message,
            "time" to notification.time,
            "date" to notification.date,
            "user" to getCurrentUserId().toString(),
        )

        return firebaseSource.saveData(NOTIFICATIONS_DOC, "", notificationMap)
    }

    fun logout(): Boolean {
        return firebaseSource.logout()
    }

    suspend fun getAdvices(): List<String> {
        return apiSource.fetchAdvices()
    }
}