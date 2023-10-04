package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi

class GroupToGroupUiMapper(private val congregationMapper: CongregationToCongregationUiMapper) :
    Mapper<Group, GroupUi>, NullableMapper<Group, GroupUi> {
    override fun map(input: Group): GroupUi {
        val groupUi = GroupUi(
            congregation = congregationMapper.map(input.congregation),
            groupNum = input.groupNum
        )
        groupUi.id = input.id
        return groupUi
    }

    override fun nullableMap(input: Group?) = input?.let { map(it) }
}