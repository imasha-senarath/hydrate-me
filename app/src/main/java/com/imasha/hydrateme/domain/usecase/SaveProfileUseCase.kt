package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun saveProfile(user: User): Boolean {
        return repository.saveProfile(user)
    }
}