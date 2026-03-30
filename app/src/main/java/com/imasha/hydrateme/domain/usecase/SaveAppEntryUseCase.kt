package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class SaveAppEntryUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke() {
        repository.saveAppEntry()
    }
}