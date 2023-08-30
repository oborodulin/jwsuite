package com.oborodulin.jwsuite.presentation_geo.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class RegionsListItem(
    val id: UUID,
    val regionCode: String,
    val regionName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = regionName,
    supportingText = regionCode
)
