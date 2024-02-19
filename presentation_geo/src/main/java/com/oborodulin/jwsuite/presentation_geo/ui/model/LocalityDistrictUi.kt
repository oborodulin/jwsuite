package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toLocalityDistrictName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi

data class LocalityDistrictUi(
    val locality: LocalityUi = LocalityUi(),
    val districtShortName: String = "",
    val districtName: String = ""
) : ModelUi()

fun LocalityDistrictUi.toLocalityDistrictsListItem() = LocalityDistrictsListItem(
    id = this.id!!,
    districtShortName = this.districtShortName,
    districtName = this.districtName
)

fun ListItemModel?.toLocalityDistrictUi() =
    LocalityDistrictUi(districtName = this?.headline.toLocalityDistrictName()).also {
        it.id = this?.itemId
    }