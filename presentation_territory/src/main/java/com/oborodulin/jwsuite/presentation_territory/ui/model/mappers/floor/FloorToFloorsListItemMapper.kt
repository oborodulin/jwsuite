package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.presentation_territory.ui.model.FloorsListItem
import java.util.UUID

class FloorToFloorsListItemMapper : Mapper<Floor, FloorsListItem> {
    override fun map(input: Floor) = FloorsListItem(
        id = input.id ?: UUID.randomUUID(),
        floorNum = input.floorNum,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        floorDesc = input.floorDesc,
        houseFullNum = input.house.houseFullNum,
        floorFullNum = input.floorFullNum,
        territoryFullCardNum = input.territoryFullCardNum,
        info = input.info
    )
}