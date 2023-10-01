package com.oborodulin.jwsuite.data.security

import java.io.InputStream
import java.io.OutputStream

interface Crypto {
    fun encrypt(rawBytes: ByteArray, outputStream: OutputStream)
    fun decrypt(inputStream: InputStream): ByteArray
}