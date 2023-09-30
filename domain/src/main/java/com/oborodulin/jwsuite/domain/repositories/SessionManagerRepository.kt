package com.oborodulin.jwsuite.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SessionManagerRepository {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    suspend fun signup(username: String, password: String)
    suspend fun login(password: String): Flow<Boolean>
    suspend fun logout()
}