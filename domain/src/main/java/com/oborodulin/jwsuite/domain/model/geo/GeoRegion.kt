package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoRegion(
    val country: GeoCountry? = null,
    val regionCode: String,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val regionName: String,
    val districts: List<GeoRegionDistrict> = emptyList(),
    val localities: List<GeoLocality> = emptyList()
) : DomainModel()
