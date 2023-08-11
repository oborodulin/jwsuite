package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

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

fun LocalityUi.toLocalitiesListItem() = LocalitiesListItem(
    id = this.id!!,
    localityCode = this.localityCode,
    localityShortName = this.localityShortName,
    localityName = this.localityName
)