package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.LocalityType

data class LocalityUi(
    val region: RegionUi = RegionUi(),
    val regionDistrict: RegionDistrictUi? = null,
    val localityCode: String = "",
    val localityType: LocalityType = LocalityType.CITY,
    val localityShortName: String = "",
    val localityName: String = ""
) : ModelUi()