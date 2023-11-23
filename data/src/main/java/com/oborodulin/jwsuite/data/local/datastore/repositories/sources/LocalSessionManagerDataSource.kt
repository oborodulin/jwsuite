package com.oborodulin.jwsuite.data.local.datastore.repositories.sources

import kotlinx.coroutines.flow.Flow

interface LocalSessionManagerDataSource {
    fun isSigned(): Flow<Boolean>
    fun username(): Flow<String?>
    fun databasePassphrase(): Flow<String>
    fun lastDestination(): Flow<String?>

    //fun roles(): Flow<List<Role>>
    //suspend fun updateRoles(roles: List<Role> = emptyList())
    suspend fun signup(username: String, password: String)
    suspend fun signout()
    fun login(password: String): Flow<String?>
    suspend fun logout(lastDestination: String? = null)
}