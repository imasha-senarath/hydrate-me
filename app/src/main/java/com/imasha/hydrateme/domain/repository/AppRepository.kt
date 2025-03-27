package com.imasha.hydrateme.domain.repository

import com.imasha.hydrateme.data.model.User

interface AppRepository {
    suspend fun login(user: User): Boolean
}