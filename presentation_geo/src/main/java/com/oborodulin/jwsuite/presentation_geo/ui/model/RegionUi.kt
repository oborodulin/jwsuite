package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class RegionUi(
    val regionCode: String = "",
    val regionName: String = ""
) : ModelUi()

fun RegionUi.toListItemModel() = ListItemModel(
    itemId = this.id ?: UUID.randomUUID(),
    headline = this.regionName,
    supportingText = this.regionCode
)