package com.oborodulin.jwsuite.data.sources.local

import androidx.datastore.core.DataStore
import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.home.common.secure.AesCipherProvider
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.domain.model.session.AuthData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.time.OffsetDateTime
import java.util.Arrays
import javax.crypto.KeyGenerator
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
    override fun username() = dataStore.data.map { authData -> authData.username }
    override fun lastDestination() = dataStore.data.map { authData -> authData.lastDestination }

    override fun databasePassphrase() = flow {
        val databasePassphrase =
            dataStore.data.map { authData -> authData.databasePassphrase }.first().ifEmpty {
                // https://github.com/Lenz-K/android-encrypted-room-database-example/blob/main/app/src/main/java/de/krbmr/encryptedroomdb/database/SecretDatabase.kt
                val keyGenerator = KeyGenerator.getInstance(AesCipherProvider.ALGORITHM)
                keyGenerator.init(AesCipherProvider.KEY_SIZE)
                // https://stackoverflow.com/questions/4931854/converting-char-array-into-byte-array-and-back-again
                val charBuffer =
                    StandardCharsets.UTF_8.decode(ByteBuffer.wrap(keyGenerator.generateKey().encoded))
                val key = Arrays.copyOf(charBuffer.array(), charBuffer.limit())
                val passphrase = AesCipherProvider.pbkdf2(key)
                dataStore.updateData { it.copy(databasePassphrase = passphrase) }
                passphrase
            }
        emit(databasePassphrase)
    }

    //override fun roles() = dataStore.data.map { authData -> decodeFromString<List<Role>>(authData.roles) }

    override suspend fun signup(username: String, password: String) = withContext(dispatcher) {
        dataStore.updateData {
            /*if (it.databasePassphrase.isEmpty()) {
                val databasePassphrase = AesCipherProvider.pbkdf2(password.toCharArray())
                it.copy(
                    username = username,
                    password = password,
                    databasePassphrase = databasePassphrase,
                    lastLoginTime = OffsetDateTime.now()
                )
            } else {*/
            it.copy(
                username = username,
                password = password,
                lastLoginTime = OffsetDateTime.now()
            )
            //}
        }
        Unit
    }

    override suspend fun signout() = withContext(dispatcher) {
        dataStore.updateData { it.copy(username = null, password = null) }
        Unit
    }

    override fun login(password: String) =
        dataStore.data.map { authData ->
            if (authData.password?.let { it == password } == true) authData.username else null
        }

    /*override suspend fun updateRoles(roles: List<Role>) = withContext(dispatcher) {
        dataStore.updateData { it.copy(roles = roles) }
        Unit
    }*/

    override suspend fun logout(lastDestination: String?) = withContext(dispatcher) {
        dataStore.updateData { it.copy(lastDestination = lastDestination) }
        Unit
    }
}
