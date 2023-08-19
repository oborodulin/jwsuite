package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

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