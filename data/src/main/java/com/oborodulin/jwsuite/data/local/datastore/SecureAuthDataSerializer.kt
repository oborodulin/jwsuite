package com.oborodulin.jwsuite.data.local.datastore

import androidx.datastore.core.Serializer
import com.oborodulin.home.common.secure.Crypto
import com.oborodulin.home.common.util.LogLevel.LOG_SECURE
import com.oborodulin.jwsuite.domain.model.session.AuthData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

private const val TAG = "Data.SecureAuthDataSerializer"

class SecureAuthDataSerializer @Inject constructor(private val crypto: Crypto) :
    Serializer<AuthData> {
    override val defaultValue: AuthData = AuthData()

    override suspend fun readFrom(input: InputStream): AuthData {
        val decryptedBytes = crypto.decrypt(inputStream = input)
        return try {
            Json.decodeFromString(decryptedBytes.decodeToString())
        } catch (e: SerializationException) {
            if (LOG_SECURE) {
                Timber.tag(TAG).e(e)
            }
            this.defaultValue
        }
    }

    override suspend fun writeTo(t: AuthData, output: OutputStream) {
        if (LOG_SECURE) {
            Timber.tag(TAG).d("writeTo: %s", t)
            Timber.tag(TAG).d("Json<AuthData> : %s", Json.encodeToString(t))
        }
        crypto.encrypt(rawBytes = Json.encodeToString(t).encodeToByteArray(), outputStream = output)
    }
}