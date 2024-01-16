package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import java.util.UUID

class MemberRoleTransferObjectToRoleTransferObjectEntityMapper :
    NullableMapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject, RoleTransferObjectEntity>,
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject, RoleTransferObjectEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject) = RoleTransferObjectEntity(
        roleTransferObjectId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        isPersonalData = input.roleTransferObjectCsv.isPersonalData,
        rtoRolesId = input.roleTransferObjectCsv.roleId,
        rtoTransferObjectsId = input.roleTransferObjectCsv.id!!
    )

    override fun nullableMap(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject?) = input?.let { map(it) }
}