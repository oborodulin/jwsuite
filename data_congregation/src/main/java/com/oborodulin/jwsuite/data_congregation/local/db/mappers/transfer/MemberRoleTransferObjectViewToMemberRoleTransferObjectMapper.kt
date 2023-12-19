package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.MemberRoleTransferObject

class MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper(private val mapper: RoleTransferObjectViewToRoleTransferObjectMapper) :
    NullableMapper<MemberRoleTransferObjectView, MemberRoleTransferObject>,
    Mapper<MemberRoleTransferObjectView, MemberRoleTransferObject> {
    override fun map(input: MemberRoleTransferObjectView): MemberRoleTransferObject {
        val member = MemberRoleTransferObject(
            memberId = input.memberRole.member.member.memberId,
            roleTransferObject = mapper.map(input.roleTransferObject)
        )
        member.id = input.roleTransferObject.roleTransferObject.roleTransferObjectId
        return member
    }

    override fun nullableMap(input: MemberRoleTransferObjectView?) = input?.let { map(it) }
}