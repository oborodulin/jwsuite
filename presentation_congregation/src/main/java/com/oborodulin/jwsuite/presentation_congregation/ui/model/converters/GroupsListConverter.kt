package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupsUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupsListToGroupsListItemMapper

class GroupsListConverter(private val mapper: GroupsListToGroupsListItemMapper) :
    CommonResultConverter<GetGroupsUseCase.Response, List<GroupsListItem>>() {
    override fun convertSuccess(data: GetGroupsUseCase.Response) = mapper.map(data.groups)
}