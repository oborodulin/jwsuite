package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.domain.model.MemberRole

class MemberRoleViewToMemberRoleMapper(private val mapper: RoleEntityToRoleMapper) :
    NullableMapper<MemberRoleView, MemberRole>, Mapper<MemberRoleView, MemberRole> {
    override fun map(input: MemberRoleView): MemberRole {
        val member = MemberRole(
            role = mapper.map(input.role),
            roleExpiredDate = input.memberRole.roleExpiredDate,
            memberId = input.memberRole.mrMembersId,
        )
        member.id = input.memberRole.memberRoleId
        return member
    }

    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}