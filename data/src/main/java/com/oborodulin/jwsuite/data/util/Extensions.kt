package com.oborodulin.jwsuite.data.util

import android.content.Context
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase

fun Context.dbVersion() =
    JwSuiteDatabase.getInstance(this).openHelper.readableDatabase.version.toString()