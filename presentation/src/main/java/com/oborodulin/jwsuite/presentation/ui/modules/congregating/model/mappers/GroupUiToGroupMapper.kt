package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi

class GroupUiToGroupMapper(private val congregationUiMapper: CongregationUiToCongregationMapper) :
    Mapper<GroupUi, Group> {
    override fun map(input: GroupUi): Group {
        val group = Group(
            congregation = congregationUiMapper.map(input.congregation),
            groupNum = input.groupNum
        )
        group.id = input.id
        return group
    }
}