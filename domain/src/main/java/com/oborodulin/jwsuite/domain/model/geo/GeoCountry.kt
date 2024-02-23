package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel
import java.math.BigDecimal

data class GeoCountry(
    val countryCode: String,
    val countryGeocode: String? = null,
    val countryOsmId: Long? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,
    val countryName: String
) : DomainModel()
