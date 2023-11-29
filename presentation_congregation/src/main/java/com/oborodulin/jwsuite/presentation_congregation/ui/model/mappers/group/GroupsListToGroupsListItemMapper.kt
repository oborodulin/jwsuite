package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem

class GroupsListToGroupsListItemMapper(mapper: GroupToGroupsListItemMapper) :
    ListMapperImpl<Group, GroupsListItem>(mapper)