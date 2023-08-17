package com.oborodulin.jwsuite.data.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data.local.db.dao.HouseDao
import com.oborodulin.jwsuite.data.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data.local.db.dao.TerritoryCategoryDao
import com.oborodulin.jwsuite.data.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryCategoryDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalAppSettingDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalEntranceDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalFloorDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalHouseDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalRoomDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalTerritoryCategoryDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalTerritoryDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourcesModule {
    @Singleton
    @Provides
    fun provideAppSettingDataSource(
        appSettingDao: AppSettingDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalAppSettingDataSource = LocalAppSettingDataSourceImpl(appSettingDao, dispatcher)

    // Territories:
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

}