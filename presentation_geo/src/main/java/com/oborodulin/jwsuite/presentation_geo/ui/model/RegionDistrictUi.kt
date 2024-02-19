package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toRegionDistrictName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi

data class RegionDistrictUi(
    val region: RegionUi = RegionUi(),
    val districtShortName: String = "",
    val districtName: String = ""
) : ModelUi()

fun ListItemModel?.toRegionDistrictUi() =
    RegionDistrictUi(districtName = this?.headline.toRegionDistrictName()).also {
        it.id = this?.itemId
    }