package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class FloorUi(
    val house: HouseUi = HouseUi(),
    val entrance: EntranceUi? = null,
    val territory: TerritoryUi? = null,
    val floorNum: Int,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val territoryDesc: String? = null,
    val floorFullNum: String = "",
    val calculatedRooms: Int? = null
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