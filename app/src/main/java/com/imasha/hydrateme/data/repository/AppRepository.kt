package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.api.ApiSource
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.firebase.FirebaseSource
import com.imasha.hydrateme.room.RecordDao
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
    private val userDao: UserDao,
    private val recordDao: RecordDao
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
        val userId = getCurrentUserId().toString()
        val localUser = userDao.getUser(userId)

        return if(localUser != null) {
            localUser
        } else {
            val onlineUser = firebaseSource.getData(USERS_DOC, userId, User::class.java)
            onlineUser.id = userId
            userDao.insertUser(onlineUser)

            onlineUser
        }
    }

    suspend fun saveProfile(user: User): Boolean {
        val userId = getCurrentUserId().toString()

        user.id = userId
        userDao.insertUser(user)

        val userMap = mapOf(
            "name" to user.name,
            "weight" to user.weight,
            "gender" to user.gender,
            "wakeUpTime" to user.wakeUpTime,
            "bedTime" to user.bedTime,
            "goal" to user.goal,
        )

        return firebaseSource.saveData(USERS_DOC, userId, userMap)
    }

    suspend fun saveDrink(record: Record): Boolean {
        //recordDao.insertRecord(record)

        val drinkMap = mapOf(
            "user" to record.user,
            "size" to record.size,
            "time" to record.time,
            "date" to record.date,
        )

        return firebaseSource.saveData(DRINKS_DOC, "", drinkMap)
    }

    suspend fun getRecords(): List<Record> {
        val userId = getCurrentUserId().toString()
        /*val localRecords = recordDao.getRecords(userId)

        if(localRecords != null) {
            return localRecords
        }*/

        return firebaseSource.getDataList(
            DRINKS_DOC, Record::class.java, mapOf("user" to getCurrentUserId().toString())
        )
    }

    suspend fun getTodayRecords(): List<Record> {
        val userId = getCurrentUserId().toString()
        val date = getCurrentDate(DD_MM_YYYY)
        /*val localRecords = recordDao.getTodayRecords(userId, date)

        if(localRecords != null) {
            return localRecords
        }*/

        return firebaseSource.getDataList(
            DRINKS_DOC,
            Record::class.java,
            mapOf("user" to userId, "date" to date)
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

    suspend fun logout(): Boolean {
        userDao.clear()
        recordDao.clear()
        //clearPref()

        return firebaseSource.logout()
    }

    suspend fun getAdvices(): List<String> {
        return apiSource.fetchAdvices()
    }
}