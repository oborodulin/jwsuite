package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoRegionDistrict(
    val region: GeoRegion,
    val districtShortName: String,
    val districtName: String,
    val localities: List<GeoLocality> = emptyList()
) : DomainModel()
