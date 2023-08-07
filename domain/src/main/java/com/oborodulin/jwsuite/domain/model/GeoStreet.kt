package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.RoadType

data class GeoStreet(
    val locality: GeoLocality,
    val localityDistrict: GeoLocalityDistrict?,
    val microdistrict: GeoMicrodistrict?,
    val streetHashCode: Int,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String,
    val houses: List<House> = emptyList()
) : DomainModel()
