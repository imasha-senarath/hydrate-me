package com.imasha.hydrateme.domain.repository

import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User

interface AppRepository {

    suspend fun userAuthentication(): Boolean

    fun getCurrentUserId(): String?

    suspend fun getFcmToken(): String

    suspend fun signUp(user: User): Boolean

    suspend fun login(user: User): Boolean

    suspend fun getProfile(): User

    suspend fun saveProfile(user: User): Boolean

    suspend fun saveDrink(record: Record): Boolean

    suspend fun getRecords(): List<Record>

    suspend fun getTodayRecords(): List<Record>

    suspend fun deleteRecord(id: String): Boolean

    suspend fun getNotifications(): List<Notification>

    suspend fun saveNotification(notification: Notification): Boolean

    suspend fun logout(): Boolean

    suspend fun getAdvices(): List<String>
}