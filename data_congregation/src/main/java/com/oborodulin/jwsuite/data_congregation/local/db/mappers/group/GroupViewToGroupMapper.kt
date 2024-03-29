package com.oborodulin.jwsuite.data_congregation.local.db.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.domain.model.congregation.Group

class GroupViewToGroupMapper(private val congregationMapper: CongregationViewToCongregationMapper) :
    Mapper<GroupView, Group>, NullableMapper<GroupView, Group> {
    override fun map(input: GroupView) = Group(
        congregation = congregationMapper.map(input.congregation),
        groupNum = input.group.groupNum
    ).also { it.id = input.group.groupId }

    override fun nullableMap(input: GroupView?) = input?.let { map(it) }
}