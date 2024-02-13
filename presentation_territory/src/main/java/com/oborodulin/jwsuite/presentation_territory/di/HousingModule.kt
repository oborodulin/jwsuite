package com.oborodulin.jwsuite.presentation_territory.di

import com.oborodulin.jwsuite.domain.usecases.entrance.DeleteEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.DeleteTerritoryEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.EntranceUseCases
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntrancesForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntrancesUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetNextEntranceNumUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetNextEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.SaveEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.SaveTerritoryEntrancesUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.DeleteFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.DeleteTerritoryFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.FloorUseCases
import com.oborodulin.jwsuite.domain.usecases.floor.GetFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.GetFloorsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.GetFloorsUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.GetNextFloorNumUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.GetNextFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.SaveFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.SaveTerritoryFloorsUseCase
import com.oborodulin.jwsuite.domain.usecases.house.DeleteHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.DeleteTerritoryHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetNextHouseNumUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetNextHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.HouseUseCases
import com.oborodulin.jwsuite.domain.usecases.house.SaveHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.SaveTerritoryHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.room.DeleteRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.DeleteTerritoryRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetNextRoomNumUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetNextRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.room.RoomUseCases
import com.oborodulin.jwsuite.domain.usecases.room.SaveRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.SaveTerritoryRoomsUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.HouseConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.HousesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceToEntranceUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceToEntrancesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceUiToEntranceMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntrancesListToEntrancesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorToFloorUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorToFloorsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorUiToFloorMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorsListToFloorsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HousesListToHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomUiToRoomMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomsListToRoomsListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HousingModule {
    // MAPPERS:
    // House:
    @Singleton
    @Provides
    fun provideHouseToHouseUiMapper(
        streetMapper: StreetToStreetUiMapper,
        localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
        microistrictMapper: MicrodistrictToMicrodistrictUiMapper,
        territoryMapper: TerritoryToTerritoryUiMapper
    ): HouseToHouseUiMapper = HouseToHouseUiMapper(
        streetMapper = streetMapper,
        localityDistrictMapper = localityDistrictMapper,
        microistrictMapper = microistrictMapper,
        territoryMapper = territoryMapper
    )

    @Singleton
    @Provides
    fun provideHouseUiToHouseMapper(
        streetUiMapper: StreetUiToStreetMapper,
        localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
        microistrictUiMapper: MicrodistrictUiToMicrodistrictMapper,
        territoryUiMapper: TerritoryUiToTerritoryMapper
    ): HouseUiToHouseMapper = HouseUiToHouseMapper(
        streetUiMapper = streetUiMapper,
        localityDistrictUiMapper = localityDistrictUiMapper,
        microistrictUiMapper = microistrictUiMapper,
        territoryUiMapper = territoryUiMapper
    )

    @Singleton
    @Provides
    fun provideHouseToHousesListItemMapper(): HouseToHousesListItemMapper =
        HouseToHousesListItemMapper()

    @Singleton
    @Provides
    fun provideHousesListToHousesListItemMapper(mapper: HouseToHousesListItemMapper): HousesListToHousesListItemMapper =
        HousesListToHousesListItemMapper(mapper = mapper)

    // Entrance:
    @Singleton
    @Provides
    fun provideEntranceToEntranceUiMapper(
        houseMapper: HouseToHouseUiMapper,
        territoryMapper: TerritoryToTerritoryUiMapper
    ): EntranceToEntranceUiMapper = EntranceToEntranceUiMapper(
        houseMapper = houseMapper, territoryMapper = territoryMapper
    )

    @Singleton
    @Provides
    fun provideEntranceUiToEntranceMapper(
        houseUiMapper: HouseUiToHouseMapper,
        territoryUiMapper: TerritoryUiToTerritoryMapper
    ): EntranceUiToEntranceMapper = EntranceUiToEntranceMapper(
        houseUiMapper = houseUiMapper, territoryUiMapper = territoryUiMapper
    )

    @Singleton
    @Provides
    fun provideEntranceToEntrancesListItemMapper(): EntranceToEntrancesListItemMapper =
        EntranceToEntrancesListItemMapper()

    @Singleton
    @Provides
    fun provideEntrancesListToEntrancesListItemMapper(mapper: EntranceToEntrancesListItemMapper): EntrancesListToEntrancesListItemMapper =
        EntrancesListToEntrancesListItemMapper(mapper = mapper)

    // Floor:
    @Singleton
    @Provides
    fun provideFloorToFloorUiMapper(
        houseMapper: HouseToHouseUiMapper,
        entranceMapper: EntranceToEntranceUiMapper,
        territoryMapper: TerritoryToTerritoryUiMapper
    ): FloorToFloorUiMapper = FloorToFloorUiMapper(
        houseMapper = houseMapper, entranceMapper = entranceMapper,
        territoryMapper = territoryMapper
    )

    @Singleton
    @Provides
    fun provideFloorUiToFloorMapper(
        houseUiMapper: HouseUiToHouseMapper,
        entranceUiMapper: EntranceUiToEntranceMapper,
        territoryUiMapper: TerritoryUiToTerritoryMapper
    ): FloorUiToFloorMapper = FloorUiToFloorMapper(
        houseUiMapper = houseUiMapper, entranceUiMapper = entranceUiMapper,
        territoryUiMapper = territoryUiMapper
    )

    @Singleton
    @Provides
    fun provideFloorToFloorsListItemMapper(): FloorToFloorsListItemMapper =
        FloorToFloorsListItemMapper()

    @Singleton
    @Provides
    fun provideFloorsListToFloorsListItemMapper(mapper: FloorToFloorsListItemMapper): FloorsListToFloorsListItemMapper =
        FloorsListToFloorsListItemMapper(mapper = mapper)

    // Room:
    @Singleton
    @Provides
    fun provideRoomToRoomUiMapper(
        localityMapper: LocalityToLocalityUiMapper,
        localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
        microistrictMapper: MicrodistrictToMicrodistrictUiMapper,
        streetMapper: StreetToStreetUiMapper,
        houseMapper: HouseToHouseUiMapper,
        entranceMapper: EntranceToEntranceUiMapper,
        floorMapper: FloorToFloorUiMapper,
        territoryMapper: TerritoryToTerritoryUiMapper
    ): RoomToRoomUiMapper = RoomToRoomUiMapper(
        localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper,
        microistrictMapper = microistrictMapper, streetMapper = streetMapper,
        houseMapper = houseMapper, entranceMapper = entranceMapper, floorMapper = floorMapper,
        territoryMapper = territoryMapper
    )

    @Singleton
    @Provides
    fun provideRoomUiToRoomMapper(
        localityUiMapper: LocalityUiToLocalityMapper,
        streetUiMapper: StreetUiToStreetMapper,
        houseUiMapper: HouseUiToHouseMapper,
        entranceUiMapper: EntranceUiToEntranceMapper,
        floorUiMapper: FloorUiToFloorMapper,
        territoryUiMapper: TerritoryUiToTerritoryMapper
    ): RoomUiToRoomMapper = RoomUiToRoomMapper(
        localityUiMapper = localityUiMapper, streetUiMapper = streetUiMapper,
        houseUiMapper = houseUiMapper, entranceUiMapper = entranceUiMapper,
        floorUiMapper = floorUiMapper, territoryUiMapper = territoryUiMapper
    )

    @Singleton
    @Provides
    fun provideRoomToRoomsListItemMapper(): RoomToRoomsListItemMapper =
        RoomToRoomsListItemMapper()

    @Singleton
    @Provides
    fun provideRoomsListToRoomsListItemMapper(mapper: RoomToRoomsListItemMapper): RoomsListToRoomsListItemMapper =
        RoomsListToRoomsListItemMapper(mapper = mapper)

    // CONVERTERS:
    // House:
    @Singleton
    @Provides
    fun provideHouseConverter(mapper: HouseToHouseUiMapper): HouseConverter =
        HouseConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideHousesListConverter(mapper: HousesListToHousesListItemMapper): HousesListConverter =
        HousesListConverter(mapper = mapper)

    // Entrance:

    // Room:
    @Singleton
    @Provides
    fun provideRoomConverter(mapper: RoomToRoomUiMapper): RoomConverter =
        RoomConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomsListConverter(mapper: RoomsListToRoomsListItemMapper): RoomsListConverter =
        RoomsListConverter(mapper = mapper)

    // USE CASES:
    // House:
    @Singleton
    @Provides
    fun provideHouseUseCases(
        getHousesUseCase: GetHousesUseCase,
        getHouseUseCase: GetHouseUseCase,
        getNextHouseUseCase: GetNextHouseUseCase,
        getNextHouseNumUseCase: GetNextHouseNumUseCase,
        getHousesForTerritoryUseCase: GetHousesForTerritoryUseCase,
        saveHouseUseCase: SaveHouseUseCase,
        saveTerritoryHousesUseCase: SaveTerritoryHousesUseCase,
        deleteHouseUseCase: DeleteHouseUseCase,
        deleteTerritoryHouseUseCase: DeleteTerritoryHouseUseCase
    ): HouseUseCases = HouseUseCases(
        getHousesUseCase,
        getHouseUseCase,
        getNextHouseUseCase,
        getNextHouseNumUseCase,
        getHousesForTerritoryUseCase,
        saveHouseUseCase,
        saveTerritoryHousesUseCase,
        deleteHouseUseCase,
        deleteTerritoryHouseUseCase
    )

    // Entrance:
    @Singleton
    @Provides
    fun provideEntranceUseCases(
        getEntrancesUseCase: GetEntrancesUseCase,
        getEntranceUseCase: GetEntranceUseCase,
        getNextEntranceUseCase: GetNextEntranceUseCase,
        getNextEntranceNumUseCase: GetNextEntranceNumUseCase,
        getEntrancesForTerritoryUseCase: GetEntrancesForTerritoryUseCase,
        saveEntranceUseCase: SaveEntranceUseCase,
        saveTerritoryEntrancesUseCase: SaveTerritoryEntrancesUseCase,
        deleteEntranceUseCase: DeleteEntranceUseCase,
        deleteTerritoryEntranceUseCase: DeleteTerritoryEntranceUseCase
    ): EntranceUseCases = EntranceUseCases(
        getEntrancesUseCase,
        getEntranceUseCase,
        getNextEntranceUseCase,
        getNextEntranceNumUseCase,
        getEntrancesForTerritoryUseCase,
        saveEntranceUseCase,
        saveTerritoryEntrancesUseCase,
        deleteEntranceUseCase,
        deleteTerritoryEntranceUseCase
    )

    // Floor:
    @Singleton
    @Provides
    fun provideFloorUseCases(
        getFloorsUseCase: GetFloorsUseCase,
        getFloorUseCase: GetFloorUseCase,
        getNextFloorUseCase: GetNextFloorUseCase,
        getNextFloorNumUseCase: GetNextFloorNumUseCase,
        getFloorsForTerritoryUseCase: GetFloorsForTerritoryUseCase,
        saveFloorUseCase: SaveFloorUseCase,
        saveTerritoryFloorsUseCase: SaveTerritoryFloorsUseCase,
        deleteFloorUseCase: DeleteFloorUseCase,
        deleteTerritoryFloorUseCase: DeleteTerritoryFloorUseCase
    ): FloorUseCases = FloorUseCases(
        getFloorsUseCase,
        getFloorUseCase,
        getNextFloorUseCase,
        getNextFloorNumUseCase,
        getFloorsForTerritoryUseCase,
        saveFloorUseCase,
        saveTerritoryFloorsUseCase,
        deleteFloorUseCase,
        deleteTerritoryFloorUseCase
    )

    // Room:
    @Singleton
    @Provides
    fun provideRoomUseCases(
        getRoomsUseCase: GetRoomsUseCase,
        getRoomUseCase: GetRoomUseCase,
        getNextRoomUseCase: GetNextRoomUseCase,
        getNextRoomNumUseCase: GetNextRoomNumUseCase,
        getRoomsForTerritoryUseCase: GetRoomsForTerritoryUseCase,
        saveRoomUseCase: SaveRoomUseCase,
        saveTerritoryRoomsUseCase: SaveTerritoryRoomsUseCase,
        deleteRoomUseCase: DeleteRoomUseCase,
        deleteTerritoryRoomUseCase: DeleteTerritoryRoomUseCase
    ): RoomUseCases = RoomUseCases(
        getRoomsUseCase,
        getRoomUseCase,
        getNextRoomUseCase,
        getNextRoomNumUseCase,
        getRoomsForTerritoryUseCase,
        saveRoomUseCase,
        saveTerritoryRoomsUseCase,
        deleteRoomUseCase,
        deleteTerritoryRoomUseCase
    )
}