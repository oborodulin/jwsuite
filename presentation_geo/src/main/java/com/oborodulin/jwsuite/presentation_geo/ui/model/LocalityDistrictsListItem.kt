package com.oborodulin.jwsuite.presentation_geo.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class LocalityDistrictsListItem(
    val id: UUID,
    val districtShortName: String,
    val districtName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = districtName,
    supportingText = districtShortName
)
