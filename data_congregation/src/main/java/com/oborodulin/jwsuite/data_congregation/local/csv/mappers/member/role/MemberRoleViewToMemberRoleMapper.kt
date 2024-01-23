package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView

class MemberRoleViewToMemberRoleMapper(
    private val memberMapper: MemberViewToMemberMapper,
    private val roleMapper: RoleEntityToRoleMapper
) :
    NullableMapper<MemberRoleView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv>, Mapper<MemberRoleView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv> {
    override fun map(input: MemberRoleView): com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv {
        val memberRoleCsv =
            com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv(
                memberCsv = memberMapper.map(input.member),
                roleCsv = roleMapper.map(input.role),
                roleExpiredDate = input.memberRole.roleExpiredDate
            )
        memberRoleCsv.id = input.memberRole.memberRoleId
        return memberRoleCsv
    }

    override fun nullableMap(input: MemberRoleView?) = input?.let { map(it) }
}