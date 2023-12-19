package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.domain.model.congregation.MemberRoleTransferObject
import java.util.UUID

class MemberRoleTransferObjectToRoleTransferObjectEntityMapper :
    NullableMapper<MemberRoleTransferObject, RoleTransferObjectEntity>,
    Mapper<MemberRoleTransferObject, RoleTransferObjectEntity> {
    override fun map(input: MemberRoleTransferObject) = RoleTransferObjectEntity(
        roleTransferObjectId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        isPersonalData = input.roleTransferObject.isPersonalData,
        rtoRolesId = input.roleTransferObject.roleId,
        rtoTransferObjectsId = input.roleTransferObject.id!!
    )

    override fun nullableMap(input: MemberRoleTransferObject?) = input?.let { map(it) }
}