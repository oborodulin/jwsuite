package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleUi

class RoleUiToRoleMapper : Mapper<RoleUi, Role>, NullableMapper<RoleUi, Role> {
    override fun map(input: RoleUi): Role {
        val role = Role(roleType = input.roleType, roleName = input.roleName)
        role.id = input.id
        return role
    }

    override fun nullableMap(input: RoleUi?) = input?.let { map(it) }
}