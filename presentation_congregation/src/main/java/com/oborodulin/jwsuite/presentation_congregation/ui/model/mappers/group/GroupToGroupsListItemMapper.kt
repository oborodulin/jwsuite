package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem
import java.util.UUID

class GroupToGroupsListItemMapper : Mapper<Group, GroupsListItem> {
    override fun map(input: Group) = GroupsListItem(
        id = input.id ?: UUID.randomUUID(),
        groupNum = input.groupNum
    )
}