package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionDistrictName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

data class RegionDistrictUi(
    val region: RegionUi? = null,
    val shortNamePrefix: String = "",
    val districtShortName: String = "",
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: CoordinatesUi = CoordinatesUi(),
    val districtName: String = "",
    val districtFullName: String = ""
) : ModelUi()

fun RegionDistrictUi?.toListItemModel() = ListItemModel(
    itemId = this?.id,
    headline = this?.districtFullName.orEmpty(),
    supportingText = GeoRegionDistrict.shortName(
        this?.shortNamePrefix.orEmpty(),
        this?.districtShortName.orEmpty()
    ).ifEmpty { null }
)

fun ListItemModel?.toRegionDistrictUi() =
    RegionDistrictUi(districtName = this?.headline.toRegionDistrictName()).also {
        it.id = this?.itemId
    }