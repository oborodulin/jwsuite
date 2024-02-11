package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.TransferDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalTransferDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
class LocalTransferDataSourceImpl @Inject constructor(
    private val transferDao: TransferDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalTransferDataSource {
    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getTransferObjectEntities() = transferDao.findAllEntities()
    override suspend fun loadTransferObjectEntities(transferObjects: List<TransferObjectEntity>) =
        withContext(dispatcher) {
            transferDao.insert(transferObjects)
        }
}