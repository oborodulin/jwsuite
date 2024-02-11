package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer.TransferObjectCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalTransferDataSource
import com.oborodulin.jwsuite.domain.repositories.TransferRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val localTransferDataSource: LocalTransferDataSource,
    private val domainMappers: TransferObjectMappers,
    private val csvMappers: TransferObjectCsvMappers
) : TransferRepository {
    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = TransferObjectEntity.TABLE_NAME)
    override fun extractTransferObjects() = localTransferDataSource.getTransferObjectEntities()
        .map(csvMappers.transferObjectEntityListToTransferObjectCsvListMapper::map)

    @CsvLoad<TransferObjectCsv>(
        fileNamePrefix = TransferObjectEntity.TABLE_NAME,
        contentType = TransferObjectCsv::class
    )
    override fun loadTransferObjects(transferObjects: List<TransferObjectCsv>) = flow {
        localTransferDataSource.loadTransferObjectEntities(
            csvMappers.transferObjectCsvListToTransferObjectEntityListMapper.map(
                transferObjects
            )
        )
        emit(transferObjects.size)
    }
}