package com.oborodulin.jwsuite.data_geo.di

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
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeoRepositoriesModule {
    @Binds
    abstract fun bindGeoCountriesRepository(repositoryImpl: GeoCountriesRepositoryImpl): GeoCountriesRepository

    @Binds
    abstract fun bindGeoRegionsRepository(repositoryImpl: GeoRegionsRepositoryImpl): GeoRegionsRepository

    @Binds
    abstract fun bindGeoRegionDistrictsRepository(repositoryImpl: GeoRegionDistrictsRepositoryImpl): GeoRegionDistrictsRepository

    @Binds
    abstract fun bindGeoLocalitiesRepository(repositoryImpl: GeoLocalitiesRepositoryImpl): GeoLocalitiesRepository

    @Binds
    abstract fun bindGeoLocalityDistrictsRepository(repositoryImpl: GeoLocalityDistrictsRepositoryImpl): GeoLocalityDistrictsRepository

    @Binds
    abstract fun bindGeoMicrodistrictsRepository(repositoryImpl: GeoMicrodistrictsRepositoryImpl): GeoMicrodistrictsRepository

    @Binds
    abstract fun bindGeoStreetsRepository(repositoryImpl: GeoStreetsRepositoryImpl): GeoStreetsRepository
    /*
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

     */
}