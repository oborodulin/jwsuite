package com.oborodulin.jwsuite.data.local.db.mappers.csv.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView

class MemberRoleViewToRoleMapper(private val mapper: RoleEntityToRoleMapper) :
    NullableMapper<MemberRoleView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv>, Mapper<MemberRoleView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv> {
    override fun map(input: MemberRoleView) = mapper.map(input.role)
    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}