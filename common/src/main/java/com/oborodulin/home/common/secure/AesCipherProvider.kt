package com.oborodulin.home.common.secure

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.oborodulin.home.common.di.SecurityModule
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.SecureRandom
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject
import javax.inject.Named


// https://blog.stylingandroid.com/datastore-security/
// https://labs.withsecure.com/publications/how-secure-is-your-android-keystore-authentication
// https://code.tutsplus.com/ru/keys-credentials-and-storage-on-android--cms-30827t
// https://www.youtube.com/watch?v=aaSck7jBDbw
class AesCipherProvider @Inject constructor(
    @Named(SecurityModule.KEY_NAME) private val keyName: String,
    private val keyStore: KeyStore,
    @Named(SecurityModule.KEY_STORE_NAME) private val keyStoreName: String
) : CipherProvider {
    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }

    override val encryptCipher: Cipher
        get() = cipher.apply {// Cipher.getInstance(TRANSFORMATION)
            init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        }

    override fun decryptCipher(iv: ByteArray): Cipher =
        cipher.apply {// Cipher.getInstance(TRANSFORMATION)
            init(Cipher.DECRYPT_MODE, getOrCreateKey(), IvParameterSpec(iv))
        }

    private fun getOrCreateKey(): SecretKey =
        (keyStore.getEntry(keyName, null) as? KeyStore.SecretKeyEntry)?.secretKey
            ?: generateKey()

    private fun generateKey(): SecretKey = KeyGenerator.getInstance(ALGORITHM, keyStoreName)
        .apply { init(keyGenParams) }
        .generateKey()

    private val keyGenParams =
        KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(BLOCK_MODE)
            setEncryptionPaddings(PADDING)
            // https://labs.withsecure.com/publications/how-secure-is-your-android-keystore-authentication
            setUserAuthenticationRequired(false) // true
            setRandomizedEncryptionRequired(true)
        }.build()

    companion object {
        const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        const val KEY_SIZE = 256

        // https://www.danielhugenroth.com/posts/2021_06_password_hashing_on_android/
        fun pbkdf2(
            password: CharArray,
            salt: ByteArray? = null,
            iterationCount: Int = 4096,
            keyLength: Int = KEY_SIZE
        ): String {
            val bytes = ByteArray(512)
            if (salt == null) {
                val random = SecureRandom()
                random.nextBytes(bytes)
            }
            val saltBytes: ByteArray = salt ?: bytes
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(password, saltBytes, iterationCount, keyLength)
            return Base64.encodeToString(factory.generateSecret(spec)!!.encoded, Base64.DEFAULT)
        }

        // https://github.com/lambdapioneer/argon2kt
        /*fun argon2(password: ByteArray, salt: ByteArray? = null): String {
            // https://stackoverflow.com/questions/46261055/how-to-generate-a-securerandom-string-of-length-n-in-java
            val bytes = ByteArray(512)
            if (salt == null) {
                val random = SecureRandom()
                random.nextBytes(bytes)
            }
            val saltBytes: ByteArray = salt ?: bytes
            val argon2Kt = Argon2Kt()
            return argon2Kt.hash(
                mode = Argon2Mode.ARGON2_ID,
                password = password, salt = saltBytes,
                tCostInIterations = 1, mCostInKibibyte = 37888
            ).encodedOutputAsString() //rawHashAsByteArray()
        }*/

        fun generatePassphrase(): String {
            // https://github.com/Lenz-K/android-encrypted-room-database-example/blob/main/app/src/main/java/de/krbmr/encryptedroomdb/database/SecretDatabase.kt
            val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
            keyGenerator.init(KEY_SIZE)
            // https://stackoverflow.com/questions/4931854/converting-char-array-into-byte-array-and-back-again
            val charBuffer =
                StandardCharsets.UTF_8.decode(ByteBuffer.wrap(keyGenerator.generateKey().encoded))
            val key = Arrays.copyOf(charBuffer.array(), charBuffer.limit())
            return pbkdf2(key)
        }
    }
}