package com.oborodulin.jwsuite.domain.services.csv

import android.content.Context
import java.io.File
import java.text.DateFormat

data class CsvConfig(
    private val ctx: Context,
    private val subDir: String? = null,
    private val prefix: String,
    private val isUseSuffix: Boolean = false,
    private val suffix: String = DateFormat
        .getDateTimeInstance()
        .format(System.currentTimeMillis())
        .replace(",", "")
        .replace(" ", "_"),
    //@Suppress("DEPRECATION")
    val parent: File = ctx.applicationContext.filesDir,
    val childPath: String = "csv${subDir.orEmpty()}",
    val fileName: String = prefix.plus("-$suffix".takeIf { isUseSuffix }.orEmpty()).plus(".csv"),
    //Environment.getExternalStorageDirectory()?.absolutePath?.plus("/Documents/Expenso") ?: ""
)