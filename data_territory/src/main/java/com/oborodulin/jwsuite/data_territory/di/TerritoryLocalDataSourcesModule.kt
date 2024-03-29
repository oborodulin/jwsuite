package com.oborodulin.jwsuite.data_territory.di

import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalEntranceDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalFloorDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalHouseDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalRoomDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryReportDataSource
import com.oborodulin.jwsuite.data_territory.local.db.sources.LocalTerritoryStreetDataSource
import com.oborodulin.jwsuite.data_territory.sources.local.LocalEntranceDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalFloorDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalHouseDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalRoomDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalTerritoryCategoryDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalTerritoryDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalTerritoryReportDataSourceImpl
import com.oborodulin.jwsuite.data_territory.sources.local.LocalTerritoryStreetDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object TerritoryLocalDataSourcesModule {
abstract class TerritoryLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalTerritoryCategoryDataSource(dataSourceImpl: LocalTerritoryCategoryDataSourceImpl): LocalTerritoryCategoryDataSource

    @Binds
    abstract fun bindLocalHouseDataSource(dataSourceImpl: LocalHouseDataSourceImpl): LocalHouseDataSource

    @Binds
    abstract fun bindLocalEntranceDataSource(dataSourceImpl: LocalEntranceDataSourceImpl): LocalEntranceDataSource

    @Binds
    abstract fun bindLocalFloorDataSource(dataSourceImpl: LocalFloorDataSourceImpl): LocalFloorDataSource

    @Binds
    abstract fun bindLocalRoomDataSource(dataSourceImpl: LocalRoomDataSourceImpl): LocalRoomDataSource

    @Binds
    abstract fun bindLocalTerritoryDataSource(dataSourceImpl: LocalTerritoryDataSourceImpl): LocalTerritoryDataSource

    @Binds
    abstract fun bindLocalTerritoryStreetDataSource(dataSourceImpl: LocalTerritoryStreetDataSourceImpl): LocalTerritoryStreetDataSource

    @Binds
    abstract fun bindLocalTerritoryReportDataSource(dataSourceImpl: LocalTerritoryReportDataSourceImpl): LocalTerritoryReportDataSource
    /*
        @Singleton
        @Provides
        fun provideLocalTerritoryCategoryDataSource(
            territoryCategoryDao: TerritoryCategoryDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalTerritoryCategoryDataSource =
            LocalTerritoryCategoryDataSourceImpl(territoryCategoryDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalHouseDataSource(
            houseDao: HouseDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalHouseDataSource = LocalHouseDataSourceImpl(houseDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalEntranceDataSource(
            entranceDao: EntranceDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalEntranceDataSource = LocalEntranceDataSourceImpl(entranceDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalFloorDataSource(
            floorDao: FloorDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalFloorDataSource = LocalFloorDataSourceImpl(floorDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalRoomDataSource(
            roomDao: RoomDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalRoomDataSource = LocalRoomDataSourceImpl(roomDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalTerritoryDataSource(
            territoryDao: TerritoryDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalTerritoryDataSource = LocalTerritoryDataSourceImpl(territoryDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalTerritoryStreetDataSource(
            territoryStreetDao: TerritoryStreetDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalTerritoryStreetDataSource =
            LocalTerritoryStreetDataSourceImpl(territoryStreetDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalTerritoryReportDataSource(
            territoryReportDao: TerritoryReportDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalTerritoryReportDataSource =
            LocalTerritoryReportDataSourceImpl(territoryReportDao, dispatcher)
     */
}