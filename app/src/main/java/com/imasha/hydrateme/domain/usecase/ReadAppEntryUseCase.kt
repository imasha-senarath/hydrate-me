package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class ReadAppEntryUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(): Boolean {
        return repository.readAppEntry()
    }
}