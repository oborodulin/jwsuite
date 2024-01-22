package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun transferObjectTableNames(transferObjects: List<Pair<TransferObjectType, Boolean>>): Flow<Map<String, Boolean>>
    fun orderedDataTableNames(): Flow<List<String>>

    // API:
    fun sqliteVersion(): Flow<String>
    fun dbVersion(): Flow<String>
    suspend fun checkpoint(): Int
}