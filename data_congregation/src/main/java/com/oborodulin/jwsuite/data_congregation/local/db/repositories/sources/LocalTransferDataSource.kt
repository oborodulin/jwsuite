package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import kotlinx.coroutines.flow.Flow

interface LocalTransferDataSource {
    // -------------------------------------- CSV Transfer --------------------------------------
    fun getTransferObjectEntities(): Flow<List<TransferObjectEntity>>
    suspend fun loadTransferObjectEntities(transferObjects: List<TransferObjectEntity>)
}