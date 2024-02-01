package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv

class MemberRoleEntityToMemberRoleCsvMapper : Mapper<MemberRoleEntity, MemberRoleCsv> {
    override fun map(input: MemberRoleEntity) = MemberRoleCsv(
        memberRoleId = input.memberRoleId,
        roleExpiredDate = input.roleExpiredDate,
        mrMembersId = input.mrMembersId,
        mrRolesId = input.mrRolesId
    )
}