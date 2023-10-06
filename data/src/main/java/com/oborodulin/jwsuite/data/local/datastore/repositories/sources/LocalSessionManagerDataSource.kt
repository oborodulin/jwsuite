package com.oborodulin.jwsuite.data.local.datastore.repositories.sources

import com.oborodulin.jwsuite.domain.model.Role
import kotlinx.coroutines.flow.Flow

interface LocalSessionManagerDataSource {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    fun username(): Flow<String?>
    fun databasePassphrase(): Flow<String>
    fun roles(): Flow<List<Role>>
    fun checkPassword(password: String): Flow<Boolean>
    suspend fun updateRoles(roles: List<Role> = emptyList())
    suspend fun signup(username: String, password: String)
    suspend fun signout()
    suspend fun login()
    suspend fun logout()
}