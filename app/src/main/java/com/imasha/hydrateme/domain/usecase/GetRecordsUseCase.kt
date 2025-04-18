package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getRecords(): List<Record> {
        return repository.getRecords()
    }
}