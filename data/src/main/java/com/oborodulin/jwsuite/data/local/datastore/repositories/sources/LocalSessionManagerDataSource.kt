package com.oborodulin.jwsuite.data.local.datastore.repositories.sources

import kotlinx.coroutines.flow.Flow

interface LocalSessionManagerDataSource {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    suspend fun signup(username: String, password: String)
    suspend fun login(password: String)
    suspend fun logout()
}