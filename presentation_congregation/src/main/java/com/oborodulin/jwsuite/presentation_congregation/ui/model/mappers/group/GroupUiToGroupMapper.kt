package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Group
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationUiToCongregationMapper

class GroupUiToGroupMapper(private val congregationUiMapper: CongregationUiToCongregationMapper) :
    Mapper<GroupUi, Group>, NullableMapper<GroupUi, Group> {
    override fun map(input: GroupUi): Group {
        val group = Group(
            congregation = congregationUiMapper.map(input.congregation),
            groupNum = input.groupNum!!
        )
        group.id = input.id
        return group
    }

    override fun nullableMap(input: GroupUi?) = input?.let { map(it) }
}