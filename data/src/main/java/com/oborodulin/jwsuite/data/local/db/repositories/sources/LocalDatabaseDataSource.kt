package com.oborodulin.jwsuite.data.local.db.repositories.sources

import kotlinx.coroutines.flow.Flow

interface LocalDatabaseDataSource {
    fun getDataTables(): Flow<List<String>>

    // API:
    suspend fun setCheckpoint(): Int
}