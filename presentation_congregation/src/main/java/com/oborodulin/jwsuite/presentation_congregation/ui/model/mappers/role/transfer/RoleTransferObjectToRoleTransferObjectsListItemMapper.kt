package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleTransferObjectsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.transfer.TransferObjectToTransferObjectsListItemMapper
import java.util.UUID

class RoleTransferObjectToRoleTransferObjectsListItemMapper(private val mapper: TransferObjectToTransferObjectsListItemMapper) :
    Mapper<RoleTransferObject, RoleTransferObjectsListItem> {
    override fun map(input: RoleTransferObject) = RoleTransferObjectsListItem(
        id = input.id ?: UUID.randomUUID(),
        isPersonalData = input.isPersonalData,
        transferObject = mapper.map(input.transferObject)
    )
}