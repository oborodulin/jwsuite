package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoStreetWithMicrodistricts(
    val street: GeoStreet,
    var microdistricts: List<GeoMicrodistrict>
) : DomainModel()