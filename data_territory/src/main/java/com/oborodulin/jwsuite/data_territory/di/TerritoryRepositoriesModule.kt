package com.oborodulin.jwsuite.data_territory.di

import com.oborodulin.jwsuite.data_territory.repositories.EntrancesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.FloorsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.HousesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.RoomsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.TerritoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.TerritoryCategoriesRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.TerritoryReportsRepositoryImpl
import com.oborodulin.jwsuite.data_territory.repositories.TerritoryStreetsRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object TerritoryRepositoriesModule {
abstract class TerritoryRepositoriesModule {
    @Binds
    abstract fun bindHousesRepository(repositoryImpl: HousesRepositoryImpl): HousesRepository

    @Binds
    abstract fun bindEntrancesRepository(repositoryImpl: EntrancesRepositoryImpl): EntrancesRepository

    @Binds
    abstract fun bindFloorsRepository(repositoryImpl: FloorsRepositoryImpl): FloorsRepository

    @Binds
    abstract fun bindRoomsRepository(repositoryImpl: RoomsRepositoryImpl): RoomsRepository

    @Binds
    abstract fun bindTerritoryCategoriesRepository(repositoryImpl: TerritoryCategoriesRepositoryImpl): TerritoryCategoriesRepository

    @Binds
    abstract fun bindTerritoryStreetsRepository(repositoryImpl: TerritoryStreetsRepositoryImpl): TerritoryStreetsRepository

    @Binds
    abstract fun bindTerritoryReportsRepository(repositoryImpl: TerritoryReportsRepositoryImpl): TerritoryReportsRepository

    @Binds
    abstract fun bindTerritoriesRepository(repositoryImpl: TerritoriesRepositoryImpl): TerritoriesRepository
    /*
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
     */
}