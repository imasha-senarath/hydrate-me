package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun signUp(user: User): Boolean {
        return repository.signUp(user)
    }
}