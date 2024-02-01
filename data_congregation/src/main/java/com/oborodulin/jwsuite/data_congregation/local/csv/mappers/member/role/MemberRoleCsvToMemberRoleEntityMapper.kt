package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv

class MemberRoleCsvToMemberRoleEntityMapper : Mapper<MemberRoleCsv, MemberRoleEntity> {
    override fun map(input: MemberRoleCsv) = MemberRoleEntity(
        memberRoleId = input.memberRoleId,
        roleExpiredDate = input.roleExpiredDate,
        mrMembersId = input.mrMembersId,
        mrRolesId = input.mrRolesId
    )
}