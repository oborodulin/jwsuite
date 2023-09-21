package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import java.util.UUID

class HouseToHousesListItemMapper : Mapper<House, HousesListItem> {
    override fun map(input: House) = HousesListItem(
        id = input.id ?: UUID.randomUUID(),
        zipCode = input.zipCode,
        houseFullNum = input.houseFullNum,
        buildingType = input.buildingType,
        isBusiness = input.isBusiness,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        isForeignLanguage = input.isForeignLanguage,
        isPrivateSector = input.isPrivateSector,
        houseExpr = input.houseExpr,
        streetFullName = input.street.streetFullName,
        territoryFullCardNum = input.territoryFullCardNum,
        info = input.info
    )
}