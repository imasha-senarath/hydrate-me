package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun logout(): Boolean {
        return repository.logout()
    }
}