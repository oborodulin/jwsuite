package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem

class GroupsListToGroupsListItemMapper(mapper: GroupToGroupsListItemMapper) :
    ListMapperImpl<Group, GroupsListItem>(mapper)