package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class RegionListItem(
    val id: UUID,
    val regionCode: String,
    val regionName: String
) : ListItemModel(
    itemId = id,
    headline = regionName,
    supportingText = regionCode
)
