package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoOsm(
    val geocode: String? = null,
    val osmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates()
) : DomainModel() {
    override fun toString() = geocode.orEmpty().plus(coordinates)
}
