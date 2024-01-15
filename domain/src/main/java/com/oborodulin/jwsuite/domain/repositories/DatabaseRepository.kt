package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun transferObjectTables(transferObjects: List<TransferObjectType>): Flow<List<String>>
    fun tablesByOrder(): Flow<List<String>>
}
