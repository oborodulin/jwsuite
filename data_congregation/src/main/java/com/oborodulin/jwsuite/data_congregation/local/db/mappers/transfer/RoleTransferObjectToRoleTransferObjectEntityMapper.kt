package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import java.util.UUID

class RoleTransferObjectToRoleTransferObjectEntityMapper :
    NullableMapper<RoleTransferObject, RoleTransferObjectEntity>,
    Mapper<RoleTransferObject, RoleTransferObjectEntity> {
    override fun map(input: RoleTransferObject) = RoleTransferObjectEntity(
        roleTransferObjectId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        isPersonalData = input.isPersonalData,
        rtoRolesId = input.roleId,
        rtoTransferObjectsId = input.transferObject.id!!
    )

    override fun nullableMap(input: RoleTransferObject?) = input?.let { map(it) }
}