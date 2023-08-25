package com.oborodulin.jwsuite.presentation.ui.modules.geo.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.RoadType

data class StreetUi(
    val locality: LocalityUi = LocalityUi(),
    val localityDistrict: LocalityDistrictUi? = null,
    val microdistrict: MicrodistrictUi? = null,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String = ""
) : ModelUi()

fun StreetUi.toStreetsListItem() = StreetsListItem(
    id = this.id!!,
    isPrivateSector = this.isPrivateSector,
    estimatedHouses = this.estimatedHouses,
    streetFullName = this.streetName
)