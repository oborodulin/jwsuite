package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import java.util.UUID

class MemberRoleToMemberRoleEntityMapper :
    NullableMapper<MemberRole, MemberRoleEntity>, Mapper<MemberRole, MemberRoleEntity> {
    override fun map(input: MemberRole) = MemberRoleEntity(
        memberRoleId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        roleExpiredDate = input.roleExpiredDate,
        mrMembersId = input.member.id!!,
        mrRolesId = input.role.id!!
    )

    override fun nullableMap(input: MemberRole?) = input?.let { map(it) }
}