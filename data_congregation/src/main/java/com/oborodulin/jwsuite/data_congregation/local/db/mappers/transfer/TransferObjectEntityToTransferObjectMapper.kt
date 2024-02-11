package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject

class TransferObjectEntityToTransferObjectMapper :
    NullableMapper<TransferObjectEntity, TransferObject>,
    Mapper<TransferObjectEntity, TransferObject> {
    override fun map(input: TransferObjectEntity): TransferObject {
        val transferObject = TransferObject(
            transferObjectType = input.transferObjectType,
            transferObjectName = input.transferObjectName
        )
        transferObject.id = input.transferObjectId
        return transferObject
    }

    override fun nullableMap(input: TransferObjectEntity?) = input?.let { map(it) }
}