package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.extensions.toIntRoomNum
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi

data class RoomUi(
    val locality: LocalityUi = LocalityUi(),
    val localityDistrict: LocalityDistrictUi? = null,
    val microdistrict: MicrodistrictUi? = null,
    val street: StreetUi = StreetUi(),
    val house: HouseUi = HouseUi(),
    val entrance: EntranceUi? = null,
    val floor: FloorUi? = null,
    val territory: TerritoryUi? = null,
    val roomNum: Int? = null,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val roomDesc: String? = null
) : ModelUi()

fun ListItemModel?.toRoomUi() =
    RoomUi(roomNum = this?.headline?.toIntRoomNum()).also { it.id = this?.itemId }
/*
fun EntranceUi.toHousesListItem() = HousesListItem(
    id = this.id!!,
    zipCode = this.zipCode,
    houseFullNum = this.houseFullNum,
    buildingType = this.buildingType,
    isBusiness = this.isBusiness,
    isSecurity = this.isSecurity,
    isIntercom = this.isIntercom,
    isResidential = this.isResidential,
    isForeignLanguage = this.isForeignLanguage,
    isPrivateSector = this.isPrivateSector,
    streetFullName = this.street.streetFullName
)
*/