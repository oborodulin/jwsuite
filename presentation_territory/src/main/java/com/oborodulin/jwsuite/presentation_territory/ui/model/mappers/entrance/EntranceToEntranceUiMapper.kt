package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntranceUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper

class EntranceToEntranceUiMapper(
    private val houseMapper: HouseToHouseUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper
) : Mapper<Entrance, EntranceUi>, NullableMapper<Entrance, EntranceUi> {
    override fun map(input: Entrance) = EntranceUi(
        house = houseMapper.map(input.house),
        territory = territoryMapper.nullableMap(input.territory),
        entranceNum = input.entranceNum,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        floorsQty = input.floorsQty,
        roomsByFloor = input.roomsByFloor,
        estimatedRooms = input.estimatedRooms,
        entranceDesc = input.entranceDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: Entrance?) = input?.let { map(it) }
}