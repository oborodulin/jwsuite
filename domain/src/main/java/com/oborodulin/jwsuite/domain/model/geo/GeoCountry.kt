package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoCountry(
    val countryCode: String = "",
    val osm: GeoOsm = GeoOsm(),
    val countryName: String = ""
) : DomainModel() {
    val osmInfo = osm.toString()
}
