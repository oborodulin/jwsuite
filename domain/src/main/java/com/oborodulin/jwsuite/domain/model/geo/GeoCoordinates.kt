package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel
import java.math.BigDecimal

data class GeoCoordinates(
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null
) : DomainModel()