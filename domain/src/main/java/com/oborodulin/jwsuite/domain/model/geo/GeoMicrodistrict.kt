package com.oborodulin.jwsuite.domain.model.geo

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.VillageType

data class GeoMicrodistrict(
    val ctx: Context? = null,
    var locality: GeoLocality? = null,
    var localityDistrict: GeoLocalityDistrict? = null,
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String = "",
    val microdistrictGeocode: String? = null,
    val microdistrictOsmId: Long? = null,
    val coordinates: GeoCoordinates = GeoCoordinates(),
    val microdistrictName: String = "",
    val streets: List<GeoStreet> = emptyList()
) : DomainModel() {
    val microdistrictFullName =
        "${
            ctx?.let { it.resources.getStringArray(R.array.village_types)[microdistrictType.ordinal] }
                .orEmpty()
        } $microdistrictName".trim()

    companion object {
        fun default(
            locality: GeoLocality? = null,
            localityDistrict: GeoLocalityDistrict? = null,
            country: GeoCountry? = null
        ): GeoMicrodistrict {
            val deflocality = GeoLocality.default(country)
            return GeoMicrodistrict(
                locality = locality ?: deflocality,
                localityDistrict = localityDistrict ?: GeoLocalityDistrict.default(
                    locality ?: deflocality
                )
            )
        }
    }
}
