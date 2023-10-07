package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.presentation_territory.ui.model.FloorUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceToEntranceUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper

class FloorToFloorUiMapper(
    private val houseMapper: HouseToHouseUiMapper,
    private val entranceMapper: EntranceToEntranceUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper
) : Mapper<Floor, FloorUi>, NullableMapper<Floor, FloorUi> {
    override fun map(input: Floor): FloorUi {
        val houseUi = FloorUi(
            house = houseMapper.map(input.house),
            entrance = entranceMapper.nullableMap(input.entrance),
            territory = territoryMapper.nullableMap(input.territory),
            floorNum = input.floorNum,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estimatedRooms,
            floorDesc = input.floorDesc
        )
        houseUi.id = input.id
        return houseUi
    }

    override fun nullableMap(input: Floor?) = input?.let { map(it) }
}