package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toLocalityDistrictName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi

data class LocalityDistrictUi(
    val locality: LocalityUi? = null,
    val districtShortName: String = "",
    val districtName: String = ""
) : ModelUi()

fun LocalityDistrictUi.toLocalityDistrictsListItem() = LocalityDistrictsListItem(
    id = this.id!!,
    districtShortName = this.districtShortName,
    districtName = this.districtName
)

fun LocalityDistrictUi?.toListItemModel() = ListItemModel(
    itemId = this?.id,
    headline = this?.districtName.orEmpty(),
    supportingText = this?.districtShortName
)

fun ListItemModel?.toLocalityDistrictUi() =
    LocalityDistrictUi(districtName = this?.headline.toLocalityDistrictName()).also {
        it.id = this?.itemId
    }