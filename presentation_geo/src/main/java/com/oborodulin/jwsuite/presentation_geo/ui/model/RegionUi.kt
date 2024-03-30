package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.types.RegionType

data class RegionUi(
    val country: CountryUi? = null,
    val codePrefix: String = "",
    val regionCode: String = "",
    val regionType: RegionType = RegionType.REGION,
    val isRegionTypePrefix: Boolean = false,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val regionName: String = "",
    val regionFullName: String = ""
) : ModelUi()

fun RegionUi?.toListItemModel() = ListItemModel(
    itemId = this?.id,
    headline = this?.regionFullName.orEmpty(),
    supportingText = GeoRegion.code(this?.codePrefix.orEmpty(), this?.regionCode.orEmpty())
        .ifEmpty { null }
)

fun ListItemModel?.toRegionUi() =
    RegionUi(regionName = this?.headline.toRegionName()).also { it.id = this?.itemId }