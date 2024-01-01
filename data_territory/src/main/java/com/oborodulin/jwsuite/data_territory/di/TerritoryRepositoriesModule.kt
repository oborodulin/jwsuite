package com.oborodulin.jwsuite.data_territory.di

import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.repositories.EntrancesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.FloorsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.HousesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.RoomsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoryCategoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.TerritoryReportsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.repositories.sources.LocalTerritoryReportDataSource
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
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
        localHouseDataSource: LocalHouseDataSource, mappers: HouseMappers
    ): HousesRepository = HousesRepositoryImpl(localHouseDataSource, mappers)

    @Singleton
    @Provides
    fun provideEntrancesRepository(
        localEntranceDataSource: LocalEntranceDataSource, mappers: EntranceMappers
    ): EntrancesRepository = EntrancesRepositoryImpl(localEntranceDataSource, mappers)

    @Singleton
    @Provides
    fun provideFloorsRepository(
        localFloorDataSource: LocalFloorDataSource, mappers: FloorMappers
    ): FloorsRepository = FloorsRepositoryImpl(localFloorDataSource, mappers)

    @Singleton
    @Provides
    fun provideRoomsRepository(
        localRoomDataSource: LocalRoomDataSource, mappers: RoomMappers
    ): RoomsRepository = RoomsRepositoryImpl(localRoomDataSource, mappers)

    @Singleton
    @Provides
    fun provideTerritoryCategoriesRepository(
        localTerritoryCategoryDataSource: LocalTerritoryCategoryDataSource,
        mappers: TerritoryCategoryMappers
    ): TerritoryCategoriesRepository =
        TerritoryCategoriesRepositoryImpl(localTerritoryCategoryDataSource, mappers)

    @Singleton
    @Provides
    fun provideTerritoryReportsRepository(
        localTerritoryReportDataSource: LocalTerritoryReportDataSource,
        mappers: TerritoryReportMappers
    ): TerritoryReportsRepository =
        TerritoryReportsRepositoryImpl(localTerritoryReportDataSource, mappers)

    @Singleton
    @Provides
    fun provideTerritoriesRepository(
        localTerritoryDataSource: LocalTerritoryDataSource,
        localHouseDataSource: LocalHouseDataSource,
        localEntranceDataSource: LocalEntranceDataSource,
        localFloorDataSource: LocalFloorDataSource,
        localRoomDataSource: LocalRoomDataSource,
        mappers: TerritoryMappers
    ): TerritoriesRepository = TerritoriesRepositoryImpl(
        localTerritoryDataSource,
        localHouseDataSource,
        localEntranceDataSource,
        localFloorDataSource,
        localRoomDataSource,
        mappers
    )
}