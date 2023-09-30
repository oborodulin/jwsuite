package com.oborodulin.jwsuite.data.local.datastore.repositories.sources

import kotlinx.coroutines.flow.Flow

interface LocalSessionManagerDataSource {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    fun checkPassword(password: String): Flow<Boolean>
    suspend fun signup(username: String, password: String)
    suspend fun login()
    suspend fun logout()
}