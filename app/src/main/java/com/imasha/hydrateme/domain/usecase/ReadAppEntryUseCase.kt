package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntryUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): Flow<Boolean> {
        return repository.readAppEntry()
    }
}