package com.oborodulin.jwsuite.data_territory.di

import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryReportCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetCsvMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.EntrancesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.FloorsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.HousesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.RoomsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoryCategoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoryReportsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoryStreetsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryReportDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryStreetDataSource
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TerritoryRepositoriesModule {
    @Singleton //@ViewModelScoped
    @Provides
    fun provideHousesRepository(
        localHouseDataSource: LocalHouseDataSource,
        domainMappers: HouseMappers, csvMappers: HouseCsvMappers
    ): HousesRepository = HousesRepositoryImpl(localHouseDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideEntrancesRepository(
        localEntranceDataSource: LocalEntranceDataSource,
        domainMappers: EntranceMappers, csvMappers: EntranceCsvMappers
    ): EntrancesRepository =
        EntrancesRepositoryImpl(localEntranceDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideFloorsRepository(
        localFloorDataSource: LocalFloorDataSource,
        domainMappers: FloorMappers, csvMappers: FloorCsvMappers
    ): FloorsRepository = FloorsRepositoryImpl(localFloorDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideRoomsRepository(
        localRoomDataSource: LocalRoomDataSource,
        domainMappers: RoomMappers, csvMappers: RoomCsvMappers
    ): RoomsRepository = RoomsRepositoryImpl(localRoomDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideTerritoryCategoriesRepository(
        localTerritoryCategoryDataSource: LocalTerritoryCategoryDataSource,
        domainMappers: TerritoryCategoryMappers, csvMappers: TerritoryCategoryCsvMappers
    ): TerritoryCategoriesRepository =
        TerritoryCategoriesRepositoryImpl(
            localTerritoryCategoryDataSource,
            domainMappers,
            csvMappers
        )

    @Singleton
    @Provides
    fun provideTerritoryStreetsRepository(
        localTerritoryDataSource: LocalTerritoryStreetDataSource,
        domainMappers: TerritoryStreetMappers, csvMappers: TerritoryStreetCsvMappers
    ): TerritoryStreetsRepository =
        TerritoryStreetsRepositoryImpl(localTerritoryDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideTerritoryReportsRepository(
        localTerritoryReportDataSource: LocalTerritoryReportDataSource,
        domainMappers: TerritoryReportMappers, csvMappers: TerritoryReportCsvMappers
    ): TerritoryReportsRepository =
        TerritoryReportsRepositoryImpl(localTerritoryReportDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideTerritoriesRepository(
        localTerritoryDataSource: LocalTerritoryDataSource,
        localHouseDataSource: LocalHouseDataSource,
        localEntranceDataSource: LocalEntranceDataSource,
        localFloorDataSource: LocalFloorDataSource,
        localRoomDataSource: LocalRoomDataSource,
        domainMappers: TerritoryMappers,
        csvMappers: TerritoryCsvMappers
    ): TerritoriesRepository = TerritoriesRepositoryImpl(
        localTerritoryDataSource,
        localHouseDataSource,
        localEntranceDataSource,
        localFloorDataSource,
        localRoomDataSource,
        domainMappers,
        csvMappers
    )
}