package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoStreetWithLocalityDistricts(
    val street: GeoStreet,
    var localityDistricts: List<GeoLocalityDistrict>
) : DomainModel()