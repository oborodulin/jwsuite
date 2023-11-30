package com.oborodulin.jwsuite.data.util

import android.content.Context
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase

fun Context.dbVersion(localSessionManagerDataSource: LocalSessionManagerDataSource) =
    JwSuiteDatabase.getInstance(
        this,
        localSessionManagerDataSource = localSessionManagerDataSource
    ).openHelper.readableDatabase.version.toString()