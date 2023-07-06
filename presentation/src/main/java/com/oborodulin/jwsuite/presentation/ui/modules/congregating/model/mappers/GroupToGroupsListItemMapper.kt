package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupsListItem
import java.util.UUID

class GroupToGroupsListItemMapper : Mapper<Group, GroupsListItem> {
    override fun map(input: Group) = GroupsListItem(
        id = input.id ?: UUID.randomUUID(),
        groupNum = input.groupNum
    )
}