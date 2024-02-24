package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class RegionUi(
    val country: CountryUi = CountryUi(),
    val regionCode: String = "",
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val regionName: String = ""
) : ModelUi()

fun RegionUi.toListItemModel() = ListItemModel(
    itemId = this.id ?: UUID.randomUUID(),
    headline = this.regionName,
    supportingText = this.regionCode
)

fun ListItemModel?.toRegionUi() =
    RegionUi(regionName = this?.headline.toRegionName()).also { it.id = this?.itemId }