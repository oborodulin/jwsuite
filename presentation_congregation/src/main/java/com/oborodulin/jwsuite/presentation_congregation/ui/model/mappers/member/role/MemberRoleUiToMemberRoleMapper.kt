package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberUiToMemberMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.RoleUiToRoleMapper

class MemberRoleUiToMemberRoleMapper(
    private val memberUiMapper: MemberUiToMemberMapper,
    private val roleUiMapper: RoleUiToRoleMapper
) : NullableMapper<MemberRoleUi, MemberRole>, Mapper<MemberRoleUi, MemberRole> {
    override fun map(input: MemberRoleUi) = MemberRole(
        member = memberUiMapper.map(input.member),
        role = roleUiMapper.map(input.role),
        roleExpiredDate = input.roleExpiredDate
    ).also { it.id = input.id }

    override fun nullableMap(input: MemberRoleUi?) = input?.let { map(it) }
}