package com.oborodulin.jwsuite.domain.model.geo

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.home.common.extensions.ifNotEmpty

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
        const val SHORT_NAME_LENGTH = 3
        fun default(region: GeoRegion? = null, country: GeoCountry? = null) =
            GeoRegionDistrict(region = region ?: GeoRegion.default(country))

        fun shortNameFromName(prefix: String = "", name: String) =
            name.substring(0..<SHORT_NAME_LENGTH).uppercase()
                .let { fn -> prefix.ifNotEmpty { "$it-$fn" } ?: fn }

        fun shortNameFromRegDistrictShortName(regDistrictShortName: String) =
            regDistrictShortName.substringAfterLast('-')
    }
}
