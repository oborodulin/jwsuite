package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupsListToGroupsListItemMapper

class GroupsListConverter(private val mapper: GroupsListToGroupsListItemMapper) :
    CommonResultConverter<GetGroupsUseCase.Response, List<GroupsListItem>>() {
    override fun convertSuccess(data: GetGroupsUseCase.Response) = mapper.map(data.groups)
}