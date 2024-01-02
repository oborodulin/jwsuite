package com.oborodulin.jwsuite.presentation_territory.di

import com.oborodulin.jwsuite.domain.usecases.territory.report.CancelProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.DeleteMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.ProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.TerritoryReportUseCases
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryMemberReportConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryMemberReportsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryReportHousesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHouseUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseUiToHouseMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportToTerritoryMemberReportUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportToTerritoryMemberReportsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportUiToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryReportHouseToTerritoryReportHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryReportHousesListToTerritoryReportHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomUiToRoomMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportingModule {
    // MAPPERS:
    // Territory Member Report:
    @Singleton
    @Provides
    fun provideTerritoryMemberReportToTerritoryMemberReportUiMapper(
        territoryStreetMapper: TerritoryStreetToTerritoryStreetUiMapper,
        houseMapper: HouseToHouseUiMapper,
        roomMapper: RoomToRoomUiMapper
    ): TerritoryMemberReportToTerritoryMemberReportUiMapper =
        TerritoryMemberReportToTerritoryMemberReportUiMapper(
            territoryStreetMapper = territoryStreetMapper,
            houseMapper = houseMapper,
            roomMapper = roomMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryMemberReportUiToTerritoryMemberReportMapper(
        territoryStreetUiMapper: TerritoryStreetUiToTerritoryStreetMapper,
        houseUiMapper: HouseUiToHouseMapper,
        roomUiMapper: RoomUiToRoomMapper
    ): TerritoryMemberReportUiToTerritoryMemberReportMapper =
        TerritoryMemberReportUiToTerritoryMemberReportMapper(
            territoryStreetUiMapper = territoryStreetUiMapper,
            houseUiMapper = houseUiMapper,
            roomUiMapper = roomUiMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryMemberReportToTerritoryMemberReportsListItemMapper(): TerritoryMemberReportToTerritoryMemberReportsListItemMapper =
        TerritoryMemberReportToTerritoryMemberReportsListItemMapper()

    @Singleton
    @Provides
    fun provideRoomsListToRoomsListItemMapper(mapper: TerritoryMemberReportToTerritoryMemberReportsListItemMapper): TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper =
        TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper(mapper = mapper)

    // Territory House Report:
    @Singleton
    @Provides
    fun provideTerritoryReportHouseToTerritoryReportHousesListItemMapper(): TerritoryReportHouseToTerritoryReportHousesListItemMapper =
        TerritoryReportHouseToTerritoryReportHousesListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryReportHousesListToTerritoryReportHousesListItemMapper(mapper: TerritoryReportHouseToTerritoryReportHousesListItemMapper): TerritoryReportHousesListToTerritoryReportHousesListItemMapper =
        TerritoryReportHousesListToTerritoryReportHousesListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Territory Member Report:
    @Singleton
    @Provides
    fun provideTerritoryMemberReportConverter(mapper: TerritoryMemberReportToTerritoryMemberReportUiMapper): TerritoryMemberReportConverter =
        TerritoryMemberReportConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMemberReportsListConverter(mapper: TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper): TerritoryMemberReportsListConverter =
        TerritoryMemberReportsListConverter(mapper = mapper)

    // Territory House Report:
    @Singleton
    @Provides
    fun provideTerritoryReportHousesListConverter(mapper: TerritoryReportHousesListToTerritoryReportHousesListItemMapper): TerritoryReportHousesListConverter =
        TerritoryReportHousesListConverter(mapper = mapper)

    // USE CASES:
    // Territory Member Report:
    @Singleton
    @Provides
    fun provideTerritoryReportUseCases(
        getMemberReportsUseCase: GetMemberReportsUseCase,
        getMemberReportUseCase: GetMemberReportUseCase,
        getReportHousesUseCase: GetReportHousesUseCase,
        getReportRoomsUseCase: GetReportRoomsUseCase,
        processMemberReportUseCase: ProcessMemberReportUseCase,
        cancelProcessMemberReportUseCase: CancelProcessMemberReportUseCase,
        saveReportHouseUseCase: SaveReportHouseUseCase,
        saveReportRoomUseCase: SaveReportRoomUseCase,
        deleteMemberReportUseCase: DeleteMemberReportUseCase
    ): TerritoryReportUseCases = TerritoryReportUseCases(
        getMemberReportsUseCase,
        getMemberReportUseCase,
        getReportHousesUseCase,
        getReportRoomsUseCase,
        processMemberReportUseCase,
        cancelProcessMemberReportUseCase,
        saveReportHouseUseCase,
        saveReportRoomUseCase,
        deleteMemberReportUseCase
    )
}