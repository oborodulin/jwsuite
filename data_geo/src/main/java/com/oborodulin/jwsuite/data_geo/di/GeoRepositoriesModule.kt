package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry.GeoCountryApiMappers
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.repositories.GeoCountriesRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoLocalitiesRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoLocalityDistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoMicrodistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoRegionDistrictsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoRegionsRepositoryImpl
import com.oborodulin.jwsuite.data_geo.repositories.GeoStreetsRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
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
    fun provideGeoCountriesRepository(
        localCountryDataSource: LocalGeoCountryDataSource,
        remoteCountryDataSource: RemoteGeoCountryDataSource,
        domainMappers: GeoCountryMappers,
        apiMappers: GeoCountryApiMappers,
        csvMappers: GeoCountryCsvMappers
    ): GeoCountriesRepository = GeoCountriesRepositoryImpl(
        localCountryDataSource,
        remoteCountryDataSource,
        domainMappers,
        apiMappers,
        csvMappers
    )

    @Singleton
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