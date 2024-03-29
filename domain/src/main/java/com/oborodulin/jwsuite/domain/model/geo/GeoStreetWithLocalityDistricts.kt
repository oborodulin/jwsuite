package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoStreetWithLocalityDistricts(
    val street: GeoStreet,
    val localityDistricts: List<GeoLocalityDistrict> = emptyList()
) : DomainModel()