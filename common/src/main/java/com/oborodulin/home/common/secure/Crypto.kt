package com.oborodulin.home.common.secure

import java.io.InputStream
import java.io.OutputStream

// https://blog.stylingandroid.com/datastore-security/
interface Crypto {
    fun encrypt(rawBytes: ByteArray, outputStream: OutputStream)
    fun decrypt(inputStream: InputStream): ByteArray
}