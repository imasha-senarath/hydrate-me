package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class UserIdUseCase @Inject constructor(private val repository: AppRepository) {

    fun getCurrentUserId(): String? {
        return repository.getCurrentUserId()
    }
}