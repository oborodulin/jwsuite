package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class EntranceUi(
    val house: HouseUi = HouseUi(),
    val territory: TerritoryUi? = null,
    val entranceNum: Int? = null,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val floorsQty: Int? = null,
    val roomsByFloor: Int? = null,
    val estimatedRooms: Int? = null,
    val territoryDesc: String? = null,
    val entranceFullNum: String = "",
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