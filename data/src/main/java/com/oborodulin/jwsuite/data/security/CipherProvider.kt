package com.oborodulin.jwsuite.data.security

import javax.crypto.Cipher

interface CipherProvider {
    val encryptCipher: Cipher
    fun decryptCipher(iv: ByteArray): Cipher
}