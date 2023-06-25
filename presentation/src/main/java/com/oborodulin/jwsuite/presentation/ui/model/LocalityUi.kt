package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.LocalityType
import java.util.UUID

data class LocalityUi(
    val regionId: UUID,
    val regionDistrictId: UUID? = null,
    val localityCode: String,
    val localityType: LocalityType,
    val localityShortName: String,
    val localityName: String
) : ModelUi()