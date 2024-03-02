package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoCountryDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoLocalityDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoLocalityDistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoMicrodistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoRegionDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoRegionDistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.local.LocalGeoStreetDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeoLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalGeoCountryDataSource(dataSourceImpl: LocalGeoCountryDataSourceImpl): LocalGeoCountryDataSource

    @Binds
    abstract fun bindLocalGeoRegionDataSource(dataSourceImpl: LocalGeoRegionDataSourceImpl): LocalGeoRegionDataSource

    @Binds
    abstract fun bindLocalGeoRegionDistrictDataSource(dataSourceImpl: LocalGeoRegionDistrictDataSourceImpl): LocalGeoRegionDistrictDataSource

    @Binds
    abstract fun bindLocalGeoLocalityDataSource(dataSourceImpl: LocalGeoLocalityDataSourceImpl): LocalGeoLocalityDataSource

    @Binds
    abstract fun bindLocalGeoLocalityDistrictDataSource(dataSourceImpl: LocalGeoLocalityDistrictDataSourceImpl): LocalGeoLocalityDistrictDataSource

    @Binds
    abstract fun bindLocalGeoMicrodistrictDataSource(dataSourceImpl: LocalGeoMicrodistrictDataSourceImpl): LocalGeoMicrodistrictDataSource

    @Binds
    abstract fun bindLocalGeoStreetDataSource(dataSourceImpl: LocalGeoStreetDataSourceImpl): LocalGeoStreetDataSource
    /*
    @Singleton
    @Provides
    fun provideLocalGeoCountryDataSource(
        countryDao: GeoCountryDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalGeoCountryDataSource = LocalGeoCountryDataSourceImpl(countryDao, dispatcher)

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
     */
}