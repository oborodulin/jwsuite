package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel
import java.math.BigDecimal

data class GeoRegion(
    val country: GeoCountry,
    val regionCode: String,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val latitude: BigDecimal? = null,
    val longitude: BigDecimal? = null,
    val regionName: String,
    val districts: List<GeoRegionDistrict> = emptyList(),
    val localities: List<GeoLocality> = emptyList()
) : DomainModel()
