package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv

class TransferObjectEntityToTransferObjectCsvMapper :
    Mapper<TransferObjectEntity, TransferObjectCsv> {
    override fun map(input: TransferObjectEntity) = TransferObjectCsv(
        transferObjectId = input.transferObjectId,
        transferObjectType = input.transferObjectType,
        transferObjectName = input.transferObjectName
    )
}