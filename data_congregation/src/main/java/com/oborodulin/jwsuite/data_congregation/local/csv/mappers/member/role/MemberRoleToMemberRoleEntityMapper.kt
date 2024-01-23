package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import java.util.UUID

class MemberRoleToMemberRoleEntityMapper :
    NullableMapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv, MemberRoleEntity>, Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv, MemberRoleEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv) = MemberRoleEntity(
        memberRoleId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        roleExpiredDate = input.roleExpiredDate,
        mrMembersId = input.memberCsv.id!!,
        mrRolesId = input.roleCsv.id!!
    )

    override fun nullableMap(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv?) = input?.let { map(it) }
}