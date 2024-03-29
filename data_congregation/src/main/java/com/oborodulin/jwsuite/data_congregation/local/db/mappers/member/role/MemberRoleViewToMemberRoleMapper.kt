package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleEntityToRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole

class MemberRoleViewToMemberRoleMapper(
    private val memberMapper: MemberViewToMemberMapper,
    private val roleMapper: RoleEntityToRoleMapper
) :
    NullableMapper<MemberRoleView, MemberRole>, Mapper<MemberRoleView, MemberRole> {
    override fun map(input: MemberRoleView): MemberRole {
        val memberRole = MemberRole(
            member = memberMapper.map(input.member),
            role = roleMapper.map(input.memberActualRole.role),
            roleExpiredDate = input.memberActualRole.memberRole.roleExpiredDate
        )
        memberRole.id = input.memberActualRole.memberRole.memberRoleId
        return memberRole
    }

    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}