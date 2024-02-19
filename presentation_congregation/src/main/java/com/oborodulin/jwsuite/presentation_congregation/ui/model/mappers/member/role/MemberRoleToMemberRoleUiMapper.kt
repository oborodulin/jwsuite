package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.RoleToRoleUiMapper

class MemberRoleToMemberRoleUiMapper(
    private val memberMapper: MemberToMemberUiMapper,
    private val roleMapper: RoleToRoleUiMapper
) : NullableMapper<MemberRole, MemberRoleUi>, Mapper<MemberRole, MemberRoleUi> {
    override fun map(input: MemberRole) = MemberRoleUi(
        member = memberMapper.map(input.member),
        role = roleMapper.map(input.role),
        roleExpiredDate = input.roleExpiredDate
    ).also { it.id = input.id }

    override fun nullableMap(input: MemberRole?) = input?.let { map(it) }
}