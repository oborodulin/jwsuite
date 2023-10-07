package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Role
import kotlinx.coroutines.flow.Flow

// https://www.quora.com/What-is-the-difference-between-sign-in-sign-up-sign-out-log-in-log-on-log-out-and-log-off-and-when-to-use-them
interface SessionManagerRepository {
    fun isSigned(): Flow<Boolean>
    fun isLogged(): Flow<Boolean>
    fun roles(): Flow<List<Role>>
    fun signup(username: String, password: String): Flow<Boolean>
    fun signout(): Flow<Boolean>
    fun login(password: String): Flow<Boolean>
    fun logout(): Flow<Boolean>
}