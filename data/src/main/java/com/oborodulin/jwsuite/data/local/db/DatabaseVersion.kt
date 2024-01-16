package com.oborodulin.jwsuite.data.local.db

data class DatabaseVersion(
    val sqliteVersion: String,
    val dbVersion: String
)