package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import java.util.UUID

data class RegionDistrictUi(
    val regionId: UUID,
    val districtShortName: String,
    val districtName: String
) : ModelUi()