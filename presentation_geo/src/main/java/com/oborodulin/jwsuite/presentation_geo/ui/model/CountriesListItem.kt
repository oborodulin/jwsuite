package com.oborodulin.jwsuite.presentation_geo.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class CountriesListItem(
    val id: UUID,
    val countryCode: String,
    val countryGeocode: String? = null,
    val countryName: String,
    val osmInfo: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = countryName,
    supportingText = countryCode.plus(osmInfo.ifEmpty { null }?.let { ": $it" }.orEmpty())
)

fun ListItemModel.toCountriesListItem() = CountriesListItem(
    id = this.itemId ?: UUID.randomUUID(),
    countryCode = this.supportingText?.substringBefore(":").orEmpty(),
    countryGeocode = this.supportingText?.substringAfter(":")?.substringBeforeLast("[").orEmpty(),
    countryName = this.headline,
    osmInfo = this.supportingText?.substringAfter(":").orEmpty()
)