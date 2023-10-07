package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceUiToEntranceMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorUiToFloorMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper

class RoomUiToRoomMapper(
    private val houseUiMapper: HouseUiToHouseMapper,
    private val entranceUiMapper: EntranceUiToEntranceMapper,
    private val floorUiMapper: FloorUiToFloorMapper,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper
) : Mapper<RoomUi, Room> {
    override fun map(input: RoomUi): Room {
        val house = Room(
            house = houseUiMapper.map(input.house),
            entrance = entranceUiMapper.nullableMap(input.entrance),
            floor = floorUiMapper.nullableMap(input.floor),
            territory = territoryUiMapper.nullableMap(input.territory),
            roomNum = input.roomNum!!,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            isForeignLanguage = input.isForeignLanguage,
            roomDesc = input.roomDesc
        )
        house.id = input.id
        return house
    }
}