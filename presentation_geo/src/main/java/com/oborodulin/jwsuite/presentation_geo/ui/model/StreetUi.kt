package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.RoadType
import java.util.UUID

data class StreetUi(
    val locality: LocalityUi = LocalityUi(),
//    val localityDistrict: LocalityDistrictUi? = null,
//    val microdistrict: MicrodistrictUi? = null,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String = "",
    val streetFullName: String = ""
) : ModelUi()

fun StreetUi.toStreetsListItem() = StreetsListItem(
    id = this.id ?: UUID.randomUUID(),
    isPrivateSector = this.isPrivateSector,
    estimatedHouses = this.estimatedHouses,
    streetFullName = this.streetFullName,
    isPrivateSectorInfo = null,
    estHousesInfo = null
)