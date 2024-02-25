package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel

data class GeoLocalityDistrict(
    var locality: GeoLocality? = null,
    val districtShortName: String,
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val districtName: String,
    val microdistricts: List<GeoMicrodistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
