package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.domain.model.Role

class RoleEntityToRoleMapper : NullableMapper<RoleEntity, Role>, Mapper<RoleEntity, Role> {
    override fun map(input: RoleEntity): Role {
        val role = Role(
            roleType = input.roleType,
            roleName = input.roleName
        )
        role.id = input.roleId
        return role
    }

    override fun nullableMap(input: RoleEntity?) = input?.let { map(it) }
}