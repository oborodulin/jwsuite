package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem
import java.util.UUID

class RoleToRolesListItemMapper : Mapper<Role, RolesListItem> {
    override fun map(input: Role) = RolesListItem(
        id = input.id ?: UUID.randomUUID(),
        roleType = input.roleType,
        roleName = input.roleName
    )
}