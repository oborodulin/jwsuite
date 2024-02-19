package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntranceUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper

class EntranceUiToEntranceMapper(
    private val houseUiMapper: HouseUiToHouseMapper,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper
) : Mapper<EntranceUi, Entrance>, NullableMapper<EntranceUi, Entrance> {
    override fun map(input: EntranceUi) = Entrance(
        house = houseUiMapper.map(input.house),
        territory = territoryUiMapper.nullableMap(input.territory),
        entranceNum = input.entranceNum!!,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        floorsQty = input.floorsQty,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        entranceDesc = input.entranceDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: EntranceUi?) = input?.let { map(it) }
}