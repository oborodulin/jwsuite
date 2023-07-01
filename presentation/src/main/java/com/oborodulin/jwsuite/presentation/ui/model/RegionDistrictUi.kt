package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class RegionDistrictUi(
    val region: RegionUi = RegionUi(),
    val districtShortName: String = "",
    val districtName: String = ""
) : ModelUi()