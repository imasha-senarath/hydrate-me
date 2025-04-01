package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun deleteRecord(id: String): Boolean {
        return repository.deleteRecord(id)
    }
}