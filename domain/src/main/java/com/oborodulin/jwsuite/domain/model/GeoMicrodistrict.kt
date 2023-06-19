package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.VillageType
import java.util.UUID

data class GeoMicrodistrict(
    val localityId: UUID,
    val localityDistrictId: UUID,
    val microdistrictType: VillageType = VillageType.MICRO_DISTRICT,
    val microdistrictShortName: String,
    val microdistrictName: String,
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
