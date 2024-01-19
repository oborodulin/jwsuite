package com.oborodulin.jwsuite.domain.services.csv

import android.content.Context
import java.text.DateFormat

data class CsvConfig(
    private val ctx: Context,
    private val prefix: String,
    private val isUseSuffix: Boolean = false,
    private val suffix: String = DateFormat
        .getDateTimeInstance()
        .format(System.currentTimeMillis())
        .replace(",", "")
        .replace(" ", "_"),

    val fileName: String = prefix.plus(if (isUseSuffix) "-$suffix" else "").plus(".csv"),
    //@Suppress("DEPRECATION")
    val hostPath: String = ctx.filesDir.path.plus("/csv")
    //Environment.getExternalStorageDirectory()?.absolutePath?.plus("/Documents/Expenso") ?: ""
)