package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.home.common.extensions.ifNotEmpty

data class GeoLocalityDistrict(
    var locality: GeoLocality? = null,
    val districtShortName: String = "",
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val districtName: String = "",
    val microdistricts: List<GeoMicrodistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel() {
    companion object {
        const val SHORT_NAME_LENGTH = 3
        fun default(locality: GeoLocality? = null, country: GeoCountry? = null) =
            GeoLocalityDistrict(locality = locality ?: GeoLocality.default(country))

        fun shortNameFromName(prefix: String = "", name: String) =
            name.substring(0..<SHORT_NAME_LENGTH).uppercase()
                .let { fn -> prefix.ifNotEmpty { "$it-$fn" } ?: fn }
    }
}
