package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class SaveNotificationUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun saveNotification(notification: Notification): Boolean {
        return repository.saveNotification(notification)
    }
}