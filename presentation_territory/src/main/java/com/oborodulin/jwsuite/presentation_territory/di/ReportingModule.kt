package com.oborodulin.jwsuite.presentation_territory.di

import com.oborodulin.jwsuite.domain.usecases.territory.report.DeleteMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryRoomsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceToEntranceUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntranceUiToEntranceMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorToFloorUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor.FloorUiToFloorMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryHouseReportToTerritoryHouseReportsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryHouseReportsListToTerritoryHouseReportsListItemMapper
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
object ReportingModule {
    // MAPPERS:
    // Territory Report:
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

    //Territory House Report:
    @Singleton
    @Provides
    fun provideTerritoryHouseReportToTerritoryHouseReportsListItemMapper(): TerritoryHouseReportToTerritoryHouseReportsListItemMapper =
        TerritoryHouseReportToTerritoryHouseReportsListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryHouseReportsListToTerritoryHouseReportsListItemMapper(mapper: TerritoryHouseReportToTerritoryHouseReportsListItemMapper): TerritoryHouseReportsListToTerritoryHouseReportsListItemMapper =
        TerritoryHouseReportsListToTerritoryHouseReportsListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Room:
    @Singleton
    @Provides
    fun provideRoomConverter(mapper: RoomToRoomUiMapper): RoomConverter =
        RoomConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomsListConverter(mapper: RoomsListToRoomsListItemMapper): RoomsListConverter =
        RoomsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryRoomsListConverter(
        territoryMapper: TerritoryToTerritoryUiMapper,
        roomsListMapper: RoomsListToRoomsListItemMapper
    ): TerritoryRoomsListConverter = TerritoryRoomsListConverter(
        territoryMapper = territoryMapper, roomsListMapper = roomsListMapper
    )

    // USE CASES:
    // Territory Report:
    @Singleton
    @Provides
    fun provideTerritoryReportUseCases(
        getMemberReportsUseCase: GetMemberReportsUseCase,
        getMemberReportUseCase: GetMemberReportUseCase,
        getReportHousesUseCase: GetReportHousesUseCase,
        getReportRoomsUseCase: GetReportRoomsUseCase,
        saveReportHouseUseCase: SaveReportHouseUseCase,
        saveReportRoomUseCase: SaveReportRoomUseCase,
        deleteMemberReportUseCase: DeleteMemberReportUseCase
    ): TerritoryReportUseCases = TerritoryReportUseCases(
        getMemberReportsUseCase,
        getMemberReportUseCase,
        getReportHousesUseCase,
        getReportRoomsUseCase,
        saveReportHouseUseCase,
        saveReportRoomUseCase,
        deleteMemberReportUseCase
    )
}