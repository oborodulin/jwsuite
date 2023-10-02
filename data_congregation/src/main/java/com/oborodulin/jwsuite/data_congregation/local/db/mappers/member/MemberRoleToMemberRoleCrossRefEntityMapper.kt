package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleCrossRefEntity
import com.oborodulin.jwsuite.domain.model.MemberRole
import java.util.UUID

class MemberRoleToMemberRoleCrossRefEntityMapper :
    NullableMapper<MemberRole, MemberRoleCrossRefEntity>,
    Mapper<MemberRole, MemberRoleCrossRefEntity> {
    override fun map(input: MemberRole) = MemberRoleCrossRefEntity(
        memberRoleId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        mrMembersId = input.memberId,
        mrRolesId = input.role.id!!
    )

    override fun nullableMap(input: MemberRole?) = input?.let { map(it) }
}