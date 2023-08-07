package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.VillageType

data class GeoMicrodistrict(
    val locality: GeoLocality,
    val localityDistrict: GeoLocalityDistrict,
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String,
    val microdistrictName: String,
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
