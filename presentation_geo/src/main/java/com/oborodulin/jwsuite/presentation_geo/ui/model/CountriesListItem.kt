package com.oborodulin.jwsuite.presentation_geo.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class CountriesListItem(
    val id: UUID,
    val countryCode: String,
    val countryName: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = countryName,
    supportingText = countryCode
)
