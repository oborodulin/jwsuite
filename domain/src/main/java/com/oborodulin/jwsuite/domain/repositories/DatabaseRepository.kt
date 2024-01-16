package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun transferObjectTableNames(transferObjects: List<TransferObjectType>): Flow<List<String>>
    fun orderedDataTableNames(): Flow<List<String>>

    // API:
    fun sqliteVersion(): Flow<String>
    fun dbVersion(): Flow<String>
    suspend fun checkpoint(): Int
}