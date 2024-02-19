package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleUi

class RoleToRoleUiMapper : NullableMapper<Role, RoleUi>, Mapper<Role, RoleUi> {
    override fun map(input: Role) =
        RoleUi(roleType = input.roleType, roleName = input.roleName).also { it.id = input.id }

    override fun nullableMap(input: Role?) = input?.let { map(it) }
}