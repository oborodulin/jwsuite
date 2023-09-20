package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntranceUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper

class EntranceUiToEntranceMapper(
    private val houseUiMapper: HouseUiToHouseMapper,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper
) : Mapper<EntranceUi, Entrance> {
    override fun map(input: EntranceUi): Entrance {
        val house = Entrance(
            house = houseUiMapper.map(input.house),
            territory = territoryUiMapper.nullableMap(input.territory),
            entranceNum = input.entranceNum!!,
            isSecurity = input.isSecurity,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            floorsQty = input.floorsQty,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estimatedRooms,
            territoryDesc = input.territoryDesc
        )
        house.id = input.id
        return house
    }
}