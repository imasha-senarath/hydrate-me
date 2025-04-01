package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getProfile(): User {
        return repository.getProfile()
    }
}