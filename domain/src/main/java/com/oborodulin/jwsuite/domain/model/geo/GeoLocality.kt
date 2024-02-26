package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.LocalityType

data class GeoLocality(
    val ctx: Context? = null,
    var region: GeoRegion? = null,
    var regionDistrict: GeoRegionDistrict? = null,
    val localityCode: String = "",
    val localityType: LocalityType = LocalityType.CITY,
    val localityShortName: String = "",
    val localityGeocode: String? = null,
    val localityOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val localityName: String = "",
    val districts: List<GeoLocalityDistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel() {
    val localityFullName =
        "${
            ctx?.let { it.resources.getStringArray(R.array.locality_types)[localityType.ordinal] }
                .orEmpty()
        } $localityName".trim()

    companion object {
        fun default(country: GeoCountry?): GeoLocality {
            val region = GeoRegion.default(country)
            return GeoLocality(
                region = region,
                regionDistrict = GeoRegionDistrict.default(region = region)
            )
        }
    }
}