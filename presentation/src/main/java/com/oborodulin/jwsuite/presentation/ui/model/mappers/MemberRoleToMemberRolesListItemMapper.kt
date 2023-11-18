package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.presentation.ui.model.MemberRolesListItem
import java.util.UUID

class MemberRoleToMemberRolesListItemMapper(private val mapper: RoleToRolesListItemMapper) :
    Mapper<MemberRole, MemberRolesListItem> {
    override fun map(input: MemberRole) = MemberRolesListItem(
        id = input.id ?: UUID.randomUUID(),
        role = mapper.map(input.role),
        roleExpiredDate = input.roleExpiredDate
    )
}