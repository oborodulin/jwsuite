package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.home.common.extensions.ifNotEmpty
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.RegionType
import java.util.Locale

data class GeoRegion(
    val ctx: Context? = null,
    var country: GeoCountry? = null,
    val regionCode: String = "",
    val regionType: RegionType = RegionType.REGION,
    val isRegionTypePrefix: Boolean = false,
    val regionGeocode: String? = null,
    val regionOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val regionName: String = "",
    val districts: List<GeoRegionDistrict> = emptyList(),
    val localities: List<GeoLocality> = emptyList()
) : DomainModel() {
    val prefix = regionCode.substringBeforeLast('-')
    val code = regionCode.substringAfterLast('-')
    val type = ctx?.let { it.resources.getStringArray(R.array.region_types)[regionType.ordinal] }
    val regionFullName = "${
        type?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            .takeIf { isRegionTypePrefix }.orEmpty()
    } $regionName ${type.takeIf { isRegionTypePrefix.not() }.orEmpty()}".trim()
    val osmInfo = regionGeocode.orEmpty().plus(coordinates)

    companion object {
        const val REGION_CODE_LENGTH = 3
        fun default(country: GeoCountry?) = GeoRegion(country = country)
        fun code(prefix: String = "", code: String) = prefix.ifNotEmpty { "$it-$code" } ?: code
    }
}
