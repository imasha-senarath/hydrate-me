package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getNotifications(): List<Notification> {
        return repository.getNotifications()
    }
}