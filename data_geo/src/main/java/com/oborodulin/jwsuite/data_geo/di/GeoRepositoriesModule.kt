package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvMappers
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
        localRegionDataSource: LocalGeoRegionDataSource,
        domainMappers: GeoRegionMappers, csvMappers: GeoRegionCsvMappers
    ): GeoRegionsRepository =
        GeoRegionsRepositoryImpl(localRegionDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictsRepository(
        localRegionDistrictDataSource: LocalGeoRegionDistrictDataSource,
        domainMappers: GeoRegionDistrictMappers, csvMappers: GeoRegionDistrictCsvMappers
    ): GeoRegionDistrictsRepository =
        GeoRegionDistrictsRepositoryImpl(localRegionDistrictDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideGeoLocalitiesRepository(
        localLocalityDataSource: LocalGeoLocalityDataSource,
        domainMappers: GeoLocalityMappers, csvMappers: GeoLocalityCsvMappers
    ): GeoLocalitiesRepository =
        GeoLocalitiesRepositoryImpl(localLocalityDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictsRepository(
        localLocalityDistrictDataSource: LocalGeoLocalityDistrictDataSource,
        domainMappers: GeoLocalityDistrictMappers, csvMappers: GeoLocalityDistrictCsvMappers
    ): GeoLocalityDistrictsRepository =
        GeoLocalityDistrictsRepositoryImpl(
            localLocalityDistrictDataSource, domainMappers, csvMappers
        )

    @Singleton
    @Provides
    fun provideGeoMicrodistrictsRepository(
        localMicrodistrictDataSource: LocalGeoMicrodistrictDataSource,
        domainMappers: GeoMicrodistrictMappers, csvMappers: GeoMicrodistrictCsvMappers
    ): GeoMicrodistrictsRepository =
        GeoMicrodistrictsRepositoryImpl(localMicrodistrictDataSource, domainMappers, csvMappers)

    @Singleton
    @Provides
    fun provideGeoStreetsRepository(
        localStreetDataSource: LocalGeoStreetDataSource,
        domainMappers: GeoStreetMappers, csvMappers: GeoStreetCsvMappers
    ): GeoStreetsRepository =
        GeoStreetsRepositoryImpl(localStreetDataSource, domainMappers, csvMappers)
}