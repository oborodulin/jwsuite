package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity

class TransferObjectEntityToTransferObjectMapper :
    NullableMapper<TransferObjectEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv>,
    Mapper<TransferObjectEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv> {
    override fun map(input: TransferObjectEntity): com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv {
        val transferObjectCsv =
            com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv(
                transferObjectType = input.transferObjectType,
                transferObjectName = input.transferObjectName
            )
        transferObjectCsv.id = input.transferObjectId
        return transferObjectCsv
    }

    override fun nullableMap(input: TransferObjectEntity?) = input?.let { map(it) }
}