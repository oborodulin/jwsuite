package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.BuildingType
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import java.util.UUID

data class HouseUi(
    val street: StreetUi = StreetUi(),
    val localityDistrictId: UUID? = null,
    val microdistrictId: UUID? = null,
    val territoryId: UUID? = null,
    val zipCode: String? = null,
    val houseNum: Int,
    val houseLetter: String? = null,
    val buildingNum: Int? = null,
    val houseFullNum: String,
    val buildingType: BuildingType = BuildingType.HOUSE,
    val isBusiness: Boolean = false,
    val isSecurity: Boolean = false,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val calculatedRooms: Int? = null,
    val isForeignLanguage: Boolean = false,
    val isPrivateSector: Boolean = false,
    val houseDesc: String? = null,
) : ModelUi()

fun HouseUi.toHousesListItem() = HousesListItem(
    id = this.id!!,
    zipCode = this.zipCode,
    houseFullNum = this.houseFullNum,
    buildingType = this.buildingType,
    isBusiness = this.isBusiness,
    isSecurity = this.isSecurity,
    isIntercom = this.isIntercom,
    isResidential = this.isResidential,
    calculatedRooms = this.calculatedRooms,
    isForeignLanguage = this.isForeignLanguage,
    isPrivateSector = this.isPrivateSector,
    houseDesc = this.houseDesc
)