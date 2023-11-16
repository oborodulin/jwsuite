package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.domain.model.congregation.Role

class MemberRoleViewToRoleMapper(private val mapper: RoleEntityToRoleMapper) :
    NullableMapper<MemberRoleView, Role>, Mapper<MemberRoleView, Role> {
    override fun map(input: MemberRoleView) = mapper.map(input.role)
    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}