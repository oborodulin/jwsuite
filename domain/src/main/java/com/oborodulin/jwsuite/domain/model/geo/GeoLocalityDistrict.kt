package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoLocalityDistrict(
    val locality: GeoLocality,
    val districtShortName: String,
    val districtName: String,
    val microdistricts: List<GeoMicrodistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
