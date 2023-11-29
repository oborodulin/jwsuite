package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole

class MemberRoleViewToMemberRoleMapper(
    private val memberMapper: MemberViewToMemberMapper,
    private val roleMapper: RoleEntityToRoleMapper
) :
    NullableMapper<MemberRoleView, MemberRole>, Mapper<MemberRoleView, MemberRole> {
    override fun map(input: MemberRoleView): MemberRole {
        val member = MemberRole(
            member = memberMapper.map(input.member),
            role = roleMapper.map(input.role),
            roleExpiredDate = input.memberRole.roleExpiredDate
        )
        member.id = input.memberRole.memberRoleId
        return member
    }

    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}