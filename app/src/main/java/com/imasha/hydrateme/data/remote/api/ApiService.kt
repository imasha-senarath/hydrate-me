package com.imasha.hydrateme.data.remote.api

import retrofit2.http.GET

interface ApiService {
    @GET("d79c60ce-80e2-4bf3-b7c5-f151c16990d5")
    suspend fun getAdvices(): List<String>
}