package com.oborodulin.jwsuite.data.sources.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
internal val KEY_IS_SIGNED = booleanPreferencesKey("key_is_signed")
internal val KEY_IS_LOGGED = booleanPreferencesKey("key_is_logged")
internal val KEY_USERNAME = stringPreferencesKey("key_username")
internal val KEY_PASSWORD = stringPreferencesKey("key_password")

@OptIn(ExperimentalCoroutinesApi::class)
class LocalSessionManagerDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalSessionManagerDataSource {
    //first()[KEY_USERNAME] != null
    override fun isSigned() = dataStore.data.map { it[KEY_IS_SIGNED] ?: false }

    override fun isLogged() = dataStore.data.map { it[KEY_IS_LOGGED] ?: false }
    override fun checkPassword(password: String) =
        dataStore.data.map { it[KEY_PASSWORD] == password }

    override suspend fun signup(username: String, password: String) {
        dataStore.edit {
            it[KEY_USERNAME] = username
            it[KEY_PASSWORD] = password
            it[KEY_IS_SIGNED] = true
        }
    }

    override suspend fun login() {
        dataStore.edit { it[KEY_IS_LOGGED] = true }
    }

    override suspend fun logout() {
        dataStore.edit { it[KEY_IS_LOGGED] = false }
    }
}
