package com.imasha.hydrateme.domain.usecase

import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.domain.repository.AppRepository
import javax.inject.Inject

class SaveDrinkUseCase @Inject constructor(private val repository: AppRepository) {

    suspend fun saveDrink(record: Record): Boolean {
        return repository.saveDrink(record)
    }
}