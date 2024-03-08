package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class CountryUi(
    val countryCode: String = "",
    val countryGeocode: String? = null,
    val countryOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val countryName: String = ""
) : ModelUi() {
    override fun toString(): String {
        val str = StringBuffer()
        str.append("CountryUi countryName = ").append(countryName)
            .append(" (countryCode = '").append(countryCode)
            .append("'). OSM: countryOsmId = ").append(countryOsmId)
            .append("; countryGeocode = ").append(countryGeocode)
            .append("; coordinates = ").append(coordinates)
            .append(" countryId = ").append(id)
        return str.toString()
    }
}

fun CountryUi?.toListItemModel() = ListItemModel(
    itemId = this?.id ?: UUID.randomUUID(),
    headline = this?.countryName.orEmpty(),
    supportingText = this?.countryCode
)

fun ListItemModel?.toCountryUi() =
    CountryUi(countryName = this?.headline.orEmpty()).also { it.id = this?.itemId }