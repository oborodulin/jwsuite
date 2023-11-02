package com.oborodulin.jwsuite.data.sources.local

import androidx.datastore.core.DataStore
import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.home.common.secure.AesCipherProvider
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.domain.model.session.AuthData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */

class LocalSessionManagerDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<AuthData>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalSessionManagerDataSource {
    //first()[KEY_USERNAME] != null
    override fun isSigned() = dataStore.data.map { authData -> !authData.username.isNullOrEmpty() }
    override fun isLogged() = dataStore.data.map { authData -> authData.isLogged }
    override fun username() = dataStore.data.map { authData -> authData.username }
    override fun isPasswordValid(password: String) =
        dataStore.data.map { authData ->
            authData.username.takeIf { authData.password?.let { it == password } ?: false }
        }

    override fun lastDestination() = dataStore.data.map { authData -> authData.lastDestination }

    override fun databasePassphrase() =
        dataStore.data.map { authData -> authData.databasePassphrase }

    override fun roles() = dataStore.data.map { authData -> authData.roles }

    override suspend fun signup(username: String, password: String) = withContext(dispatcher) {
        dataStore.updateData {
            if (it.databasePassphrase.isEmpty()) {
                val databasePassphrase = AesCipherProvider.argon2(password.toByteArray())
                it.copy(
                    username = username,
                    password = password,
                    databasePassphrase = databasePassphrase,
                    lastLoginTime = OffsetDateTime.now(),
                    isLogged = true
                )
            } else {
                it.copy(
                    username = username,
                    password = password,
                    lastLoginTime = OffsetDateTime.now(),
                    isLogged = true
                )
            }
        }
        Unit
    }

    override suspend fun signout() = withContext(dispatcher) {
        dataStore.updateData { it.copy(username = null, password = null, isLogged = false) }
        Unit
    }

    override suspend fun login() = withContext(dispatcher) {
        dataStore.updateData { it.copy(isLogged = true, lastLoginTime = OffsetDateTime.now()) }
        Unit
    }

    override suspend fun updateRoles(roles: List<Role>) = withContext(dispatcher) {
        dataStore.updateData { it.copy(roles = roles) }
        Unit
    }

    override suspend fun logout(lastDestination: String?) = withContext(dispatcher) {
        dataStore.updateData { it.copy(isLogged = false, lastDestination = lastDestination) }
        Unit
    }
}
