package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

class MemberRoleTransferObjectViewToRoleTransferObjectMapper(private val mapper: TransferObjectEntityToTransferObjectMapper) :
    NullableMapper<MemberRoleTransferObjectView, RoleTransferObject>,
    Mapper<MemberRoleTransferObjectView, RoleTransferObject> {
    override fun map(input: MemberRoleTransferObjectView): RoleTransferObject {
        val member = RoleTransferObject(
            memberId = input.memberRole.member.memberId,
            roleId = input.roleTransferObject.rtoRolesId,
            isPersonalData = input.roleTransferObject.isPersonalData,
            transferObject = mapper.map(input.transferObject),
        )
        member.id = input.roleTransferObject.roleTransferObjectId
        return member
    }

    override fun nullableMap(input: MemberRoleTransferObjectView?) = input?.let { map(it) }
}