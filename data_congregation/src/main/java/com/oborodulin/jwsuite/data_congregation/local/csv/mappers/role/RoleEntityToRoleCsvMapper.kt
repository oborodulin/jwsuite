package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv

class RoleEntityToRoleCsvMapper : Mapper<RoleEntity, RoleCsv> {
    override fun map(input: RoleEntity) = RoleCsv(
        roleId = input.roleId,
        roleType = input.roleType,
        roleName = input.roleName
    )
}