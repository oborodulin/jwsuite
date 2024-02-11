package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv

class TransferObjectCsvToTransferObjectEntityMapper :
    Mapper<TransferObjectCsv, TransferObjectEntity> {
    override fun map(input: TransferObjectCsv) = TransferObjectEntity(
        transferObjectId = input.transferObjectId,
        transferObjectType = input.transferObjectType,
        transferObjectName = input.transferObjectName
    )
}