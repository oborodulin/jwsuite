package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.Role
import kotlinx.coroutines.flow.Flow

interface SessionManagerRepository {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    fun roles(): Flow<List<Role>>
    suspend fun signup(username: String, password: String)
    suspend fun login(password: String): Flow<Boolean>
    suspend fun logout()
}