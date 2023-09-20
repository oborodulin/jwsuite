package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class RoomUi(
    val house: HouseUi = HouseUi(),
    val entrance: EntranceUi? = null,
    val floor: FloorUi? = null,
    val territory: TerritoryUi? = null,
    val roomNum: Int? = null,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val territoryDesc: String? = null,
    var fullRoomNum: String = ""
) : ModelUi()
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