package com.oborodulin.jwsuite.domain.model

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.util.LocalityType

data class GeoLocality(
    val ctx: Context? = null,
    val region: GeoRegion,
    val regionDistrict: GeoRegionDistrict? = null,
    val localityCode: String,
    val localityType: LocalityType,
    val localityShortName: String,
    val localityName: String,
    val districts: List<GeoLocalityDistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel() {
    val localityFullName =
        "${
            ctx?.let { it.resources.getStringArray(R.array.locality_types)[localityType.ordinal] }
                .orEmpty()
        } $localityName".trim()
}
