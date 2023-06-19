package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.LocalityType
import java.util.UUID

data class GeoLocality(
    val regionId: UUID,
    val regionDistrictId: UUID? = null,
    val localityCode: String,
    val localityType: LocalityType,
    val localityName: String,
    val districts: List<GeoLocalityDistrict> = emptyList(),
    val streets: List<GeoStreet> = emptyList()
) : DomainModel()
