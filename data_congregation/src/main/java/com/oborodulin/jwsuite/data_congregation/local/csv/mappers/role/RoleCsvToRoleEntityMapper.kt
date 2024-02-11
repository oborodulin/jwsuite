package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv

class RoleCsvToRoleEntityMapper : Mapper<RoleCsv, RoleEntity> {
    override fun map(input: RoleCsv) = RoleEntity(
        roleId = input.roleId,
        roleType = input.roleType,
        roleName = input.roleName
    )
}