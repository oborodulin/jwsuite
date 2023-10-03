package com.oborodulin.jwsuite.data.local.datastore.repositories.sources

import com.oborodulin.jwsuite.domain.model.Role
import kotlinx.coroutines.flow.Flow

interface LocalSessionManagerDataSource {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    fun username(): Flow<String?>
    fun checkPassword(password: String): Flow<Boolean>
    fun roles(): Flow<List<Role>>
    suspend fun signup(username: String, password: String)
    suspend fun login()
    suspend fun updateRoles(roles: List<Role> = emptyList())
    suspend fun logout()
}