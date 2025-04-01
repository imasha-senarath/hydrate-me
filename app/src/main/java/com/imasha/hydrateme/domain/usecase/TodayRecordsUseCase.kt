package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class TodayRecordsUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun getTodayRecords(): List<Record> {
        return repository.getTodayRecords()
    }
}