package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import com.oborodulin.home.common.ui.model.ModelUi

data class GroupUi(
    val congregation: CongregationUi = CongregationUi(),
    val groupNum: Int = 1
) : ModelUi()

fun GroupUi.toGroupsListItem() = GroupsListItem(
    id = this.id!!,
    groupNum = this.groupNum
)