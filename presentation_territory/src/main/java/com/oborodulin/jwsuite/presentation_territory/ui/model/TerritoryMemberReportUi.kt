package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import java.util.UUID

data class TerritoryMemberReportUi(
    val territoryStreet: TerritoryStreetUi? = null,
    val house: HouseUi? = null,
    val room: RoomUi? = null,
    val territoryId: UUID? = null,
    val territoryMemberId: UUID,
    val territoryReportMark: TerritoryReportMark = TerritoryReportMark.PP,
    val languageCode: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val isProcessed: Boolean = false,
    val territoryReportDesc: String? = null
) : ModelUi()
/*
fun TerritoryReportUi.toHousesListItem() = HousesListItem(
    id = this.id!!,
    zipCode = this.zipCode,
    houseNum = this.houseNum ?: 0,
    houseFullNum = this.houseFullNum,
    buildingType = this.buildingType,
    isBusiness = this.isBusiness,
    isSecurity = this.isSecurity,
    isIntercom = this.isIntercom,
    isResidential = this.isResidential,
    isForeignLanguage = this.isForeignLanguage,
    isPrivateSector = this.isPrivateSector,
    streetFullName = this.street.streetFullName
)*/