package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun userAuthentication(): Boolean {
        return repository.userAuthentication()
    }
}