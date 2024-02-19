package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.presentation_territory.ui.model.FloorUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceUiToEntranceMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper

class FloorUiToFloorMapper(
    private val houseUiMapper: HouseUiToHouseMapper,
    private val entranceUiMapper: EntranceUiToEntranceMapper,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper
) : Mapper<FloorUi, Floor>, NullableMapper<FloorUi, Floor> {
    override fun map(input: FloorUi) = Floor(
        house = houseUiMapper.map(input.house),
        entrance = entranceUiMapper.nullableMap(input.entrance),
        territory = territoryUiMapper.nullableMap(input.territory),
        floorNum = input.floorNum!!,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        floorDesc = input.floorDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: FloorUi?) = input?.let { map(it) }
}