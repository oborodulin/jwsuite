package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceToEntranceUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorToFloorUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper

class RoomToRoomUiMapper(
    private val localityMapper: LocalityToLocalityUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
    private val microistrictMapper: MicrodistrictToMicrodistrictUiMapper,
    private val streetMapper: StreetToStreetUiMapper,
    private val houseMapper: HouseToHouseUiMapper,
    private val entranceMapper: EntranceToEntranceUiMapper,
    private val floorMapper: FloorToFloorUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper
) : Mapper<Room, RoomUi>, NullableMapper<Room, RoomUi> {
    override fun map(input: Room): RoomUi {
        val houseUi = RoomUi(
            locality = localityMapper.map(input.locality),
            localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
            microdistrict = microistrictMapper.nullableMap(input.microdistrict),
            street = streetMapper.map(input.street),
            house = houseMapper.map(input.house),
            entrance = entranceMapper.nullableMap(input.entrance),
            floor = floorMapper.nullableMap(input.floor),
            territory = territoryMapper.nullableMap(input.territory),
            roomNum = input.roomNum,
            isIntercom = input.isIntercom,
            isResidential = input.isResidential,
            isForeignLanguage = input.isForeignLanguage,
            roomDesc = input.roomDesc
        )
        houseUi.id = input.id
        return houseUi
    }

    override fun nullableMap(input: Room?) = input?.let { map(it) }
}