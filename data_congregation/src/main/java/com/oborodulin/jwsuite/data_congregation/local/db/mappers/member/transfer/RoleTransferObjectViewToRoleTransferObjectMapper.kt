package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

class RoleTransferObjectViewToRoleTransferObjectMapper(private val mapper: TransferObjectEntityToTransferObjectMapper) :
    NullableMapper<RoleTransferObjectView, RoleTransferObject>,
    Mapper<RoleTransferObjectView, RoleTransferObject> {
    override fun map(input: RoleTransferObjectView): RoleTransferObject {
        val roleTransferObject = RoleTransferObject(
            roleId = input.roleTransferObject.rtoRolesId,
            isPersonalData = input.roleTransferObject.isPersonalData,
            transferObject = mapper.map(input.transferObject)
        )
        roleTransferObject.id = input.roleTransferObject.roleTransferObjectId
        return roleTransferObject
    }

    override fun nullableMap(input: RoleTransferObjectView?) = input?.let { map(it) }
}