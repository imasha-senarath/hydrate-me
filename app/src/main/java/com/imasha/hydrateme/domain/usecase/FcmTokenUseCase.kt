package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class FcmTokenUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getFcmToken(): String {
        return repository.getFcmToken()
    }
}