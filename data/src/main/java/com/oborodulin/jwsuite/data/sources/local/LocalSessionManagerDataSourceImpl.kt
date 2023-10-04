package com.oborodulin.jwsuite.data.sources.local

import androidx.datastore.core.DataStore
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.domain.model.AuthData
import com.oborodulin.jwsuite.domain.model.Role
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */

@OptIn(ExperimentalCoroutinesApi::class)
class LocalSessionManagerDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<AuthData>
) : LocalSessionManagerDataSource {
    //first()[KEY_USERNAME] != null
    override fun isSigned() = dataStore.data.map { authData -> !authData.username.isNullOrEmpty() }
    override fun isLogged() = dataStore.data.map { authData -> authData.isLogged }
    override fun username() = dataStore.data.map { authData -> authData.username }
    override fun checkPassword(password: String) =
        dataStore.data.map { authData -> authData.password?.let { it == password } ?: false }

    override fun databasePassphrase() = dataStore.data.map { authData -> authData.databasePassphrase }

    override fun roles() = dataStore.data.map { authData -> authData.roles }

    override suspend fun signup(username: String, password: String) {
        dataStore.updateData {
            AuthData(
                username = username,
                password = password,
                databasePassphrase = password,
                isLogged = true
            )
        }
    }

    override suspend fun login() {
        dataStore.updateData { it.copy(isLogged = true, lastLoginTime = OffsetDateTime.now()) }
    }

    override suspend fun updateRoles(roles: List<Role>) {
        dataStore.updateData { it.copy(roles = roles) }
    }

    override suspend fun logout() {
        dataStore.updateData { it.copy(isLogged = false) }
    }
}
