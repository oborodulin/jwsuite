package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Group
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi

class GroupToGroupUiMapper(private val congregationMapper: CongregationToCongregationUiMapper) :
    Mapper<Group, GroupUi> {
    override fun map(input: Group): GroupUi {
        val groupUi = GroupUi(
            congregation = congregationMapper.map(input.congregation),
            groupNum = input.groupNum
        )
        groupUi.id = input.id
        return groupUi
    }
}