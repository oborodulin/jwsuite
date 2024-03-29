package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.extensions.toStreetName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.RoadType

data class StreetUi(
    val locality: LocalityUi? = null,
//    val localityDistrict: LocalityDistrictUi? = null,
//    val microdistrict: MicrodistrictUi? = null,
    val roadType: RoadType = RoadType.STREET,
    val isPrivateSector: Boolean = false,
    val estimatedHouses: Int? = null,
    val streetName: String = "",
    val streetFullName: String = ""
) : ModelUi()

fun StreetUi.toStreetsListItem() = StreetsListItem(
    id = this.id!!,
    isPrivateSector = this.isPrivateSector,
    estimatedHouses = this.estimatedHouses,
    streetFullName = this.streetFullName,
    isPrivateSectorInfo = null,
    estHousesInfo = null
)

fun ListItemModel?.toStreetUi() =
    StreetUi(streetName = this?.headline.toStreetName()).also { it.id = this?.itemId }