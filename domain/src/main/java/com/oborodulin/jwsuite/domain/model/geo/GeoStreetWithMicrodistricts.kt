package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoStreetWithMicrodistricts(
    val street: GeoStreet,
    val microdistricts: List<GeoMicrodistrict> = emptyList()
) : DomainModel()