package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class GroupUi(
    val congregation: CongregationUi = CongregationUi(),
    val groupNum: Int? = null
) : ModelUi()

fun GroupUi.toListItemModel() = ListItemModel(
    itemId = this.id ?: UUID.randomUUID(),
    headline = this.groupNum?.toString().orEmpty()
)