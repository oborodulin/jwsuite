package com.oborodulin.jwsuite.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DataStoreUtil {
    /**
     * serializes data type into string
     * performs encryption
     * stores encrypted data in DataStore
     */
    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue =
                security.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }

    /**
     * fetches encrypted data from DataStore
     * performs decryption
     * deserializes data into respective data type
     */
    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map {
            val decryptedValue = security.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            Json { encodeDefaults = true }.decodeFromString(decryptedValue)
        }
    }


    /**
     * demonstrates use of [secureMap]
     */
    fun getUserInfo() = dataStore.data
        .secureMap<LoginResModel> { prefs ->
            prefs[USER_INFO].orEmpty()
        }

    /**
     * demonstrates use of [secureEdit]
     */
    suspend fun setUserInfo(value: LoginResModel) {
        dataStore.secureEdit(value) { prefs, encryptedValue ->
            prefs[USER_INFO] = encryptedValue
        }
    }
}