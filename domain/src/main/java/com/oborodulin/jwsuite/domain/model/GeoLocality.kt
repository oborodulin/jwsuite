package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.LocalityType

data class GeoLocality(
    val region: GeoRegion,
    val regionDistrict: GeoRegionDistrict? = null,
    val localityCode: String,
    val localityType: LocalityType,
    val localityShortName: String,
    val localityName: String,
    val districts: List<GeoLocalityDistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
