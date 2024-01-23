package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity

class RoleEntityToRoleMapper : NullableMapper<RoleEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv>, Mapper<RoleEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv> {
    override fun map(input: RoleEntity): com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv {
        val roleCsv = com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv(
            roleType = input.roleType,
            roleName = input.roleName
        )
        roleCsv.id = input.roleId
        return roleCsv
    }

    override fun nullableMap(input: RoleEntity?) = input?.let { map(it) }
}