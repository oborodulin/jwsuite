package com.oborodulin.jwsuite.data.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data.local.db.dao.HouseDao
import com.oborodulin.jwsuite.data.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalCongregationDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGroupDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalMemberDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalAppSettingDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalCongregationDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalEntranceDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalFloorDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoLocalityDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoLocalityDistrictDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoMicrodistrictDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoRegionDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoRegionDistrictDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGeoStreetDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalGroupDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalHouseDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalMemberDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalRoomDataSourceImpl
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

    // Geo:
    @Singleton
    @Provides
    fun provideLocalGeoRegionDataSource(
        regionDao: GeoRegionDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoRegionDataSource = LocalGeoRegionDataSourceImpl(regionDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGeoRegionDistrictDataSource(
        regionDistrictDao: GeoRegionDistrictDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoRegionDistrictDataSource =
        LocalGeoRegionDistrictDataSourceImpl(regionDistrictDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGeoLocalityDataSource(
        localityDao: GeoLocalityDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoLocalityDataSource = LocalGeoLocalityDataSourceImpl(localityDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGeoLocalityDistrictDataSource(
        localityDistrictDao: GeoLocalityDistrictDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoLocalityDistrictDataSource =
        LocalGeoLocalityDistrictDataSourceImpl(localityDistrictDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGeoMicrodistrictDataSource(
        microdistrictDao: GeoMicrodistrictDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoMicrodistrictDataSource =
        LocalGeoMicrodistrictDataSourceImpl(microdistrictDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGeoStreetDataSource(
        streetDao: GeoStreetDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoStreetDataSource = LocalGeoStreetDataSourceImpl(streetDao, dispatcher)

    // Congregations:
    @Singleton
    @Provides
    fun provideLocalCongregationDataSource(
        congregationDao: CongregationDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalCongregationDataSource = LocalCongregationDataSourceImpl(congregationDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalGroupDataSource(
        groupDao: GroupDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGroupDataSource = LocalGroupDataSourceImpl(groupDao, dispatcher)

    @Singleton
    @Provides
    fun provideLocalMemberDataSource(
        memberDao: MemberDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalMemberDataSource = LocalMemberDataSourceImpl(memberDao, dispatcher)

    // Territories:
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