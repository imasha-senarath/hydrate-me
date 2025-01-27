package com.imasha.hydrateme.api

import com.imasha.hydrateme.utils.AppConstants.ADVICES_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ADVICES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}