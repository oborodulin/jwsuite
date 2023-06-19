package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class GeoLocalityDistrict(
    val localityId: UUID,
    val districtShortName: String,
    val districtName: String,
    val microdistricts: List<GeoMicrodistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
