package com.imasha.hydrateme.api

import javax.inject.Inject

class ApiSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchAdvices(): List<String> {
        return apiService.getAdvices()
    }
}