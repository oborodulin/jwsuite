package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

data class GeoStreet(
    val localityId: UUID,
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String,
    val houses: List<House> = emptyList()
) : DomainModel()
