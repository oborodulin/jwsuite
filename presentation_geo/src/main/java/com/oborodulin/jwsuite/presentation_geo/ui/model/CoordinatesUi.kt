package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.math.BigDecimal

data class CoordinatesUi(
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
) : DomainModel()