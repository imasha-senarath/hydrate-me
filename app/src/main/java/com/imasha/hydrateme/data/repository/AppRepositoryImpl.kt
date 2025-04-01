package com.imasha.hydrateme.data.repository

import com.imasha.hydrateme.data.remote.api.ApiSource
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.remote.firebase.FirebaseSource
import com.imasha.hydrateme.data.local.RecordDao
import com.imasha.hydrateme.data.local.UserDao
import com.imasha.hydrateme.domain.repository.AppRepository
import com.imasha.hydrateme.utils.AppConstants.DRINKS_DOC
import com.imasha.hydrateme.utils.AppConstants.NOTIFICATIONS_DOC
import com.imasha.hydrateme.utils.AppConstants.USERS_DOC
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val apiSource: ApiSource,
    private val userDao: UserDao,
    private val recordDao: RecordDao
) : AppRepository {

    override suspend fun userAuthentication(): Boolean {
        return firebaseSource.userAuthentication()
    }

    override fun getCurrentUserId(): String? {
        return firebaseSource.getCurrentUserId()
    }

    override suspend fun getFcmToken(): String {
        return firebaseSource.getFcmToken()
    }

    override suspend fun login(user: User): Boolean {
        return firebaseSource.login(user)
    }

    override suspend fun signUp(user: User): Boolean {
        return firebaseSource.signUp(user)
    }

    override suspend fun getProfile(): User {
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

    override suspend fun saveProfile(user: User): Boolean {
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

    override suspend fun saveDrink(record: Record): Boolean {
        //recordDao.insertRecord(record)

        val drinkMap = mapOf(
            "user" to record.user,
            "size" to record.size,
            "time" to record.time,
            "date" to record.date,
        )

        return firebaseSource.saveData(DRINKS_DOC, "", drinkMap)
    }

    override suspend fun getRecords(): List<Record> {
        val userId = getCurrentUserId().toString()
        /*val localRecords = recordDao.getRecords(userId)

        if(localRecords != null) {
            return localRecords
        }*/

        return firebaseSource.getDataList(
            DRINKS_DOC, Record::class.java, mapOf("user" to getCurrentUserId().toString())
        )
    }

    override suspend fun getTodayRecords(): List<Record> {
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

    override suspend fun deleteRecord(id: String): Boolean {
        return firebaseSource.deleteData(DRINKS_DOC, id)
    }

    override suspend fun getNotifications(): List<Notification> {
        return firebaseSource.getDataList(
            NOTIFICATIONS_DOC,
            Notification::class.java,
            mapOf("user" to getCurrentUserId().toString())
        )
    }

    override suspend fun saveNotification(notification: Notification): Boolean {
        val notificationMap = mapOf(
            "title" to notification.title,
            "message" to notification.message,
            "time" to notification.time,
            "date" to notification.date,
            "user" to getCurrentUserId().toString(),
        )

        return firebaseSource.saveData(NOTIFICATIONS_DOC, "", notificationMap)
    }

    override suspend fun logout(): Boolean {
        userDao.clear()
        recordDao.clear()
        //clearPref()

        return firebaseSource.logout()
    }

    override suspend fun getAdvices(): List<String> {
        return apiSource.fetchAdvices()
    }
}