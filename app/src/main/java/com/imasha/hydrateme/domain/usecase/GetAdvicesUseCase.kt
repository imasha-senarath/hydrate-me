package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class GetAdvicesUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getAdvices(): List<String> {
        return repository.getAdvices()
    }
}