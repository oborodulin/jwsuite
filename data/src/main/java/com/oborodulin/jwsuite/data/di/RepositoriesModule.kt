package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingMappers
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalCongregationDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalEntranceDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalFloorDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalHouseDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalRoomDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalTerritoryDataSource
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideAppSettingsRepository(
        localAppSettingDataSource: LocalAppSettingDataSource, mappers: AppSettingMappers
    ): AppSettingsRepository = AppSettingsRepositoryImpl(localAppSettingDataSource, mappers)

    // Geo:
    @Singleton
    @Provides
    fun provideGeoRegionsRepository(
        localRegionDataSource: LocalGeoRegionDataSource, mappers: GeoRegionMappers
    ): GeoRegionsRepository = GeoRegionsRepositoryImpl(localRegionDataSource, mappers)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictsRepository(
        localRegionDistrictDataSource: LocalGeoRegionDistrictDataSource,
        mappers: GeoRegionDistrictMappers
    ): GeoRegionDistrictsRepository =
        GeoRegionDistrictsRepositoryImpl(localRegionDistrictDataSource, mappers)

    @Singleton
    @Provides
    fun provideGeoLocalitiesRepository(
        localLocalityDataSource: LocalGeoLocalityDataSource, mappers: GeoLocalityMappers
    ): GeoLocalitiesRepository = GeoLocalitiesRepositoryImpl(localLocalityDataSource, mappers)

    // Congregations:
    @Singleton
    @Provides
    fun provideCongregationsRepository(
        localCongregationDataSource: LocalCongregationDataSource,
        mappers: CongregationMappers
    ): CongregationsRepository =
        CongregationsRepositoryImpl(localCongregationDataSource, mappers)

    // Territories:
    @Singleton
    @Provides
    fun provideTerritoriesRepository(
        localTerritoryDataSource: LocalTerritoryDataSource,
        localGeoStreetDataSource: LocalGeoStreetDataSource,
        localHouseDataSource: LocalHouseDataSource,
        localEntranceDataSource: LocalEntranceDataSource,
        localFloorDataSource: LocalFloorDataSource,
        localRoomDataSource: LocalRoomDataSource,
        mappers: TerritoryMappers
    ): TerritoriesRepository = TerritoriesRepositoryImpl(
        localTerritoryDataSource,
        localGeoStreetDataSource,
        localHouseDataSource,
        localEntranceDataSource,
        localFloorDataSource,
        localRoomDataSource,
        mappers
    )
}