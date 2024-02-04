package com.oborodulin.jwsuite.domain.di

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.entrance.DeleteEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.DeleteTerritoryEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntrancesForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetEntrancesUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetNextEntranceNumUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.GetNextEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.SaveEntranceUseCase
import com.oborodulin.jwsuite.domain.usecases.entrance.SaveTerritoryEntrancesUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.DeleteFloorUseCase
import com.oborodulin.jwsuite.domain.usecases.floor.DeleteTerritoryFloorUseCase
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
import com.oborodulin.jwsuite.domain.usecases.house.SaveHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.SaveTerritoryHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.room.DeleteRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.DeleteTerritoryRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetNextRoomNumUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetNextRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.room.SaveRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.SaveTerritoryRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetCongregationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetNextTerritoryNumUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetProcessAndLocationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.HandOutTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.ProcessTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.CancelProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.DeleteMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.ProcessMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveMemberReportUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.report.SaveReportRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.DeleteTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.SaveTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TerritoryUseCasesModule {
    // Territory:
    @Singleton
    @Provides
    fun provideGetTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetTerritoryUseCase = GetTerritoryUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetNextTerritoryNumUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetNextTerritoryNumUseCase = GetNextTerritoryNumUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetProcessAndLocationTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository,
        territoryStreetsRepository: TerritoryStreetsRepository
    ): GetProcessAndLocationTerritoriesUseCase =
        GetProcessAndLocationTerritoriesUseCase(
            configuration, territoriesRepository, territoryStreetsRepository
        )

    @Singleton
    @Provides
    fun provideGetCongregationTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetCongregationTerritoriesUseCase =
        GetCongregationTerritoriesUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): DeleteTerritoryUseCase = DeleteTerritoryUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): SaveTerritoryUseCase = SaveTerritoryUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoryLocationsUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetTerritoryLocationsUseCase =
        GetTerritoryLocationsUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoryDetailUseCase(
        configuration: UseCase.Configuration,
        @ApplicationContext ctx: Context, territoriesRepository: TerritoriesRepository
    ): GetTerritoryDetailsUseCase =
        GetTerritoryDetailsUseCase(configuration, ctx, territoriesRepository)

    @Singleton
    @Provides
    fun provideHandOutTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): HandOutTerritoriesUseCase = HandOutTerritoriesUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideProcessTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): ProcessTerritoriesUseCase = ProcessTerritoriesUseCase(configuration, territoriesRepository)

    // Territory Category:
    @Singleton
    @Provides
    fun provideGetTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): GetTerritoryCategoryUseCase =
        GetTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoryCategoriesUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): GetTerritoryCategoriesUseCase =
        GetTerritoryCategoriesUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): DeleteTerritoryCategoryUseCase =
        DeleteTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): SaveTerritoryCategoryUseCase =
        SaveTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

    // Territory Street:
    @Singleton
    @Provides
    fun provideGetTerritoryStreetUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository,
        territoryStreetsRepository: TerritoryStreetsRepository,
        housesRepository: HousesRepository
    ): GetTerritoryStreetUseCase =
        GetTerritoryStreetUseCase(
            configuration,
            territoriesRepository,
            territoryStreetsRepository,
            housesRepository
        )

    @Singleton
    @Provides
    fun provideGetTerritoryStreetsUseCase(
        configuration: UseCase.Configuration, territoryStreetsRepository: TerritoryStreetsRepository
    ): GetTerritoryStreetsUseCase =
        GetTerritoryStreetsUseCase(configuration, territoryStreetsRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryStreetUseCase(
        configuration: UseCase.Configuration, territoryStreetsRepository: TerritoryStreetsRepository
    ): DeleteTerritoryStreetUseCase =
        DeleteTerritoryStreetUseCase(configuration, territoryStreetsRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryStreetUseCase(
        configuration: UseCase.Configuration,
        territoryStreetsRepository: TerritoryStreetsRepository,
        housesRepository: HousesRepository
    ): SaveTerritoryStreetUseCase =
        SaveTerritoryStreetUseCase(configuration, territoryStreetsRepository, housesRepository)

    // House:
    @Singleton
    @Provides
    fun provideGetHouseUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): GetHouseUseCase = GetHouseUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideGetNextHouseUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): GetNextHouseUseCase = GetNextHouseUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideGetHousesUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): GetHousesUseCase = GetHousesUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideGetNextHouseNumUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): GetNextHouseNumUseCase = GetNextHouseNumUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideGetHousesForTerritoryUseCase(
        configuration: UseCase.Configuration,
        territoriesRepository: TerritoriesRepository,
        housesRepository: HousesRepository
    ): GetHousesForTerritoryUseCase =
        GetHousesForTerritoryUseCase(configuration, territoriesRepository, housesRepository)

    @Singleton
    @Provides
    fun provideDeleteHouseUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): DeleteHouseUseCase = DeleteHouseUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryHouseUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): DeleteTerritoryHouseUseCase = DeleteTerritoryHouseUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideSaveHouseUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): SaveHouseUseCase = SaveHouseUseCase(configuration, housesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryHousesUseCase(
        configuration: UseCase.Configuration, housesRepository: HousesRepository
    ): SaveTerritoryHousesUseCase = SaveTerritoryHousesUseCase(configuration, housesRepository)

    // Entrance:
    @Singleton
    @Provides
    fun provideGetEntranceUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): GetEntranceUseCase = GetEntranceUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideGetNextEntranceUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): GetNextEntranceUseCase = GetNextEntranceUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideGetEntrancesUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): GetEntrancesUseCase = GetEntrancesUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideGetNextEntranceNumUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): GetNextEntranceNumUseCase = GetNextEntranceNumUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideGetEntrancesForTerritoryUseCase(
        configuration: UseCase.Configuration,
        territoriesRepository: TerritoriesRepository,
        entrancesRepository: EntrancesRepository
    ): GetEntrancesForTerritoryUseCase =
        GetEntrancesForTerritoryUseCase(configuration, territoriesRepository, entrancesRepository)

    @Singleton
    @Provides
    fun provideDeleteEntranceUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): DeleteEntranceUseCase = DeleteEntranceUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryEntranceUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): DeleteTerritoryEntranceUseCase =
        DeleteTerritoryEntranceUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideSaveEntranceUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): SaveEntranceUseCase = SaveEntranceUseCase(configuration, entrancesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryEntrancesUseCase(
        configuration: UseCase.Configuration, entrancesRepository: EntrancesRepository
    ): SaveTerritoryEntrancesUseCase =
        SaveTerritoryEntrancesUseCase(configuration, entrancesRepository)

    // Floor:
    @Singleton
    @Provides
    fun provideGetFloorUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): GetFloorUseCase = GetFloorUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideGetNextFloorUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): GetNextFloorUseCase = GetNextFloorUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideGetFloorsUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): GetFloorsUseCase = GetFloorsUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideGetNextFloorNumUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): GetNextFloorNumUseCase = GetNextFloorNumUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideGetFloorsForTerritoryUseCase(
        configuration: UseCase.Configuration,
        territoriesRepository: TerritoriesRepository,
        floorsRepository: FloorsRepository
    ): GetFloorsForTerritoryUseCase =
        GetFloorsForTerritoryUseCase(configuration, territoriesRepository, floorsRepository)

    @Singleton
    @Provides
    fun provideDeleteFloorUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): DeleteFloorUseCase = DeleteFloorUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryFloorUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): DeleteTerritoryFloorUseCase = DeleteTerritoryFloorUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideSaveFloorUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): SaveFloorUseCase = SaveFloorUseCase(configuration, floorsRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryFloorsUseCase(
        configuration: UseCase.Configuration, floorsRepository: FloorsRepository
    ): SaveTerritoryFloorsUseCase = SaveTerritoryFloorsUseCase(configuration, floorsRepository)

    // Room:
    @Singleton
    @Provides
    fun provideGetRoomUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): GetRoomUseCase = GetRoomUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideGetNextRoomUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): GetNextRoomUseCase = GetNextRoomUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideGetRoomsUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): GetRoomsUseCase = GetRoomsUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideGetNextRoomNumUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): GetNextRoomNumUseCase = GetNextRoomNumUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideGetRoomsForTerritoryUseCase(
        configuration: UseCase.Configuration,
        territoriesRepository: TerritoriesRepository,
        roomsRepository: RoomsRepository
    ): GetRoomsForTerritoryUseCase =
        GetRoomsForTerritoryUseCase(configuration, territoriesRepository, roomsRepository)

    @Singleton
    @Provides
    fun provideDeleteRoomUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): DeleteRoomUseCase = DeleteRoomUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryRoomUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): DeleteTerritoryRoomUseCase = DeleteTerritoryRoomUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideSaveRoomUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): SaveRoomUseCase = SaveRoomUseCase(configuration, roomsRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryRoomsUseCase(
        configuration: UseCase.Configuration, roomsRepository: RoomsRepository
    ): SaveTerritoryRoomsUseCase = SaveTerritoryRoomsUseCase(configuration, roomsRepository)

    // Territory Report:
    @Singleton
    @Provides
    fun provideGetMemberReportUseCase(
        configuration: UseCase.Configuration,
        territoryReportsRepository: TerritoryReportsRepository
    ): GetMemberReportUseCase =
        GetMemberReportUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideGetMemberReportsUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): GetMemberReportsUseCase = GetMemberReportsUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideGetReportHousesUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): GetReportHousesUseCase = GetReportHousesUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideGetReportRoomsUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): GetReportRoomsUseCase = GetReportRoomsUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideProcessMemberReportUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): ProcessMemberReportUseCase =
        ProcessMemberReportUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideCancelProcessMemberReportUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): CancelProcessMemberReportUseCase =
        CancelProcessMemberReportUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideDeleteMemberReportUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): DeleteMemberReportUseCase =
        DeleteMemberReportUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideSaveMemberReportUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): SaveMemberReportUseCase = SaveMemberReportUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideSaveReportHouseUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): SaveReportHouseUseCase = SaveReportHouseUseCase(configuration, territoryReportsRepository)

    @Singleton
    @Provides
    fun provideSaveReportRoomUseCase(
        configuration: UseCase.Configuration, territoryReportsRepository: TerritoryReportsRepository
    ): SaveReportRoomUseCase = SaveReportRoomUseCase(configuration, territoryReportsRepository)
}