package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingMappers
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton //@ViewModelScoped
    @Provides
    fun provideAppSettingsRepository(
        localAppSettingDataSource: LocalAppSettingDataSource, mappers: AppSettingMappers
    ): AppSettingsRepository = AppSettingsRepositoryImpl(localAppSettingDataSource, mappers)

    // Territories:
    @Singleton
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