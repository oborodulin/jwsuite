package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoLocalitiesRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoLocalityDistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoMicrodistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoRegionDistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoRegionsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.GeoStreetsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoRepositoriesModule {
    @Singleton //@ViewModelScoped
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

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictsRepository(
        localLocalityDistrictDataSource: LocalGeoLocalityDistrictDataSource,
        mappers: GeoLocalityDistrictMappers
    ): GeoLocalityDistrictsRepository =
        GeoLocalityDistrictsRepositoryImpl(localLocalityDistrictDataSource, mappers)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictsRepository(
        localMicrodistrictDataSource: LocalGeoMicrodistrictDataSource,
        mappers: GeoMicrodistrictMappers
    ): GeoMicrodistrictsRepository =
        GeoMicrodistrictsRepositoryImpl(localMicrodistrictDataSource, mappers)

    @Singleton
    @Provides
    fun provideGeoStreetsRepository(
        localStreetDataSource: LocalGeoStreetDataSource, mappers: GeoStreetMappers
    ): GeoStreetsRepository = GeoStreetsRepositoryImpl(localStreetDataSource, mappers)
}