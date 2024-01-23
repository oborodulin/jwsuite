package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView

class GroupViewToGroupMapper(private val congregationMapper: CongregationViewToCongregationMapper) :
    Mapper<GroupView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv>, NullableMapper<GroupView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv> {
    override fun map(input: GroupView): com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv {
        val groupCsv = com.oborodulin.jwsuite.domain.services.csv.model.congregation.GroupCsv(
            congregation = congregationMapper.map(input.congregation),
            groupNum = input.group.groupNum
        )
        groupCsv.id = input.group.groupId
        return groupCsv
    }

    override fun nullableMap(input: GroupView?) = input?.let { map(it) }
}