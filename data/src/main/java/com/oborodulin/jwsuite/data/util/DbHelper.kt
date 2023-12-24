package com.oborodulin.jwsuite.data.util

import android.content.Context
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase


fun Context.dbVersion(localSessionManagerDataSource: LocalSessionManagerDataSource) =
    JwSuiteDatabase.getInstance(
        this,
        localSessionManagerDataSource = localSessionManagerDataSource
    ).openHelper.readableDatabase.version.toString()

// https://stackoverflow.com/questions/3386667/query-if-android-database-exists
fun Context.dbExists(): Boolean {
    val dbFile = this.getDatabasePath(Constants.DATABASE_NAME)
    return dbFile.exists()
}