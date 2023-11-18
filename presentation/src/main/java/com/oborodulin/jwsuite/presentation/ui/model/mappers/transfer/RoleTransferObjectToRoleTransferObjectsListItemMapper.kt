package com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import com.oborodulin.jwsuite.presentation.ui.model.RoleTransferObjectsListItem
import java.util.UUID

class RoleTransferObjectToRoleTransferObjectsListItemMapper(private val mapper: TransferObjectToTransferObjectsListItemMapper) :
    Mapper<RoleTransferObject, RoleTransferObjectsListItem> {
    override fun map(input: RoleTransferObject) = RoleTransferObjectsListItem(
        id = input.id ?: UUID.randomUUID(),
        isPersonalData = input.isPersonalData,
        transferObject = mapper.map(input.transferObject)
    )
}