package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.RegionType

data class GeoRegion(
    val ctx: Context? = null,
    var country: GeoCountry? = null,
    val regionCode: String = "",
    val regionType: RegionType = RegionType.REGION,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val regionName: String = "",
    val districts: List<GeoRegionDistrict> = emptyList(),
    val localities: List<GeoLocality> = emptyList()
) : DomainModel() {
    val regionFullName =
        "$regionName ${
            ctx?.let { it.resources.getStringArray(R.array.region_types)[regionType.ordinal] }
                .orEmpty()
        }".trim()
    val osmInfo = regionGeocode.orEmpty().plus(coordinates)

    companion object {
        const val REGION_CODE_LENGTH = 3
        fun default(country: GeoCountry?) = GeoRegion(country = country)
    }
}
