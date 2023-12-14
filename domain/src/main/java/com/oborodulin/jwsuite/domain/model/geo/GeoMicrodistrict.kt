package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.VillageType

data class GeoMicrodistrict(
    val ctx: Context? = null,
    val locality: GeoLocality,
    val localityDistrict: GeoLocalityDistrict,
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String,
    val microdistrictName: String,
    val streets: List<GeoStreet> = emptyList()
) : DomainModel() {
    val microdistrictFullName =
        "${
            ctx?.let { it.resources.getStringArray(R.array.village_types)[microdistrictType.ordinal] }
                .orEmpty()
        } $microdistrictName".trim()
}
