package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.home.common.extensions.ifNotEmpty
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.RegionDistrictType

data class GeoRegionDistrict(
    val ctx: Context? = null,
    var region: GeoRegion? = null,
    val districtShortName: String = "",
    val districtType: RegionDistrictType = RegionDistrictType.BOROUGH,
    val districtGeocode: String? = null,
    val districtOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val districtName: String = "",
    val localities: List<GeoLocality> = emptyList()
) : DomainModel() {
    val prefix = districtShortName.substringBeforeLast('-')
    val shortName = districtShortName.substringAfterLast('-')
    val districtFullName =
        "$districtName ${ctx?.let { it.resources.getStringArray(R.array.region_district_types)[districtType.ordinal] }}".trim()

    companion object {
        const val SHORT_NAME_LENGTH = 3
        fun default(region: GeoRegion? = null, country: GeoCountry? = null) =
            GeoRegionDistrict(region = region ?: GeoRegion.default(country))

        fun shortName(prefix: String = "", shortName: String) =
            prefix.ifNotEmpty { "$it-$shortName" } ?: shortName

        fun shortNameFromName(prefix: String = "", name: String) =
            name.substring(0..<SHORT_NAME_LENGTH).uppercase()
                .let { fn -> prefix.ifNotEmpty { "$it-$fn" } ?: fn }
    }
}