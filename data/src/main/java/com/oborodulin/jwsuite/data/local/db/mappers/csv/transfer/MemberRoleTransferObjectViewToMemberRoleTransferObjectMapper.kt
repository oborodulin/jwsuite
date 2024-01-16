package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView

class MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper(private val mapper: RoleTransferObjectViewToRoleTransferObjectMapper) :
    NullableMapper<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject>,
    Mapper<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject> {
    override fun map(input: MemberRoleTransferObjectView): com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject {
        val member =
            com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject(
                memberId = input.memberRole.member.member.memberId,
                roleTransferObjectCsv = mapper.map(input.roleTransferObject)
            )
        member.id = input.roleTransferObject.roleTransferObject.roleTransferObjectId
        return member
    }

    override fun nullableMap(input: MemberRoleTransferObjectView?) = input?.let { map(it) }
}