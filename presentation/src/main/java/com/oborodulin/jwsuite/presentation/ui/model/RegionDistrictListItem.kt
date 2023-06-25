package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class RegionDistrictListItem(
    val id: UUID,
    val districtShortName: String,
    val districtName: String
) : ListItemModel(
    itemId = id,
    headline = districtName,
    supportingText = districtShortName
)
