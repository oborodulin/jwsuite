package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject
import com.oborodulin.jwsuite.presentation_congregation.ui.model.TransferObjectsListItem
import java.util.UUID

class TransferObjectToTransferObjectsListItemMapper :
    Mapper<TransferObject, TransferObjectsListItem> {
    override fun map(input: TransferObject) = TransferObjectsListItem(
        id = input.id ?: UUID.randomUUID(),
        transferObjectType = input.transferObjectType,
        transferObjectName = input.transferObjectName
    )
}