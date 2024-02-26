package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoRegionDistrict(
    var region: GeoRegion? = null,
    val districtShortName: String = "",
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val districtName: String = "",
    val localities: List<GeoLocality> = emptyList()
) : DomainModel() {
    companion object {
        fun default(region: GeoRegion? = null, country: GeoCountry? = null) =
            GeoRegionDistrict(region = region ?: GeoRegion.default(country))
    }
}
