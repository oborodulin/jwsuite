package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class GeoRegionDistrict(
    val regionId: UUID,
    val districtShortName: String,
    val districtName: String,
    val localities: List<GeoLocality> = emptyList()
) : DomainModel()
