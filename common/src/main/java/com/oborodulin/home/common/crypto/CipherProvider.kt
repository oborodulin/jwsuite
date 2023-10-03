package com.oborodulin.home.common.crypto

import javax.crypto.Cipher

// https://blog.stylingandroid.com/datastore-security/
interface CipherProvider {
    val encryptCipher: Cipher
    fun decryptCipher(iv: ByteArray): Cipher
}