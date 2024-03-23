package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoCountry(
    val countryCode: String = "",
    val countryGeocode: String? = null,
    val countryOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val countryName: String = ""
) : DomainModel() {
    val osmInfo = countryGeocode.orEmpty().plus(coordinates)
}
