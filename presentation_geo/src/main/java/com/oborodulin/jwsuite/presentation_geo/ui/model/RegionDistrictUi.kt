package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionDistrictName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class RegionDistrictUi(
    val region: RegionUi = RegionUi(),
    val districtShortName: String = "",
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val districtName: String = ""
) : ModelUi()

fun RegionDistrictUi?.toListItemModel() = ListItemModel(
    itemId = this?.id ?: UUID.randomUUID(),
    headline = this?.districtName.orEmpty(),
    supportingText = this?.districtShortName
)

fun ListItemModel?.toRegionDistrictUi() =
    RegionDistrictUi(districtName = this?.headline.toRegionDistrictName()).also {
        it.id = this?.itemId
    }