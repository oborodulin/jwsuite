package com.oborodulin.jwsuite.data_geo.di

import android.content.Context
import com.oborodulin.jwsuite.data_geo.remote.osm.service.CountryService
import com.oborodulin.jwsuite.data_geo.remote.osm.service.RegionService
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.sources.remote.RemoteGeoCountryDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.remote.RemoteGeoRegionDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//abstract class GeoRemoteDataSourcesModule {
object GeoRemoteDataSourcesModule {
    /*@Binds
    abstract fun bindRemoteGeoCountryDataSource(dataSourceImpl: RemoteGeoCountryDataSourceImpl): RemoteGeoCountryDataSource

    @Binds
    abstract fun bindRemoteGeoRegionDataSource(dataSourceImpl: RemoteGeoRegionDataSourceImpl): RemoteGeoRegionDataSource*/

    @Singleton
    @Provides
    fun provideRemoteGeoCountryDataSource(
        countryService: CountryService
    ): RemoteGeoCountryDataSource = RemoteGeoCountryDataSourceImpl(countryService = countryService)

    @Singleton
    @Provides
    fun provideRemoteGeoRegionDataSource(
        @ApplicationContext ctx: Context, regionService: RegionService
    ): RemoteGeoRegionDataSource =
        RemoteGeoRegionDataSourceImpl(ctx = ctx, regionService = regionService)
/*
    @Singleton
    @Provides
    fun provideRemoteGeoRegionDistrictDataSource(
        @ApplicationContext ctx: Context, regionDistrictService: RegionDistrictService
    ): RemoteGeoRegionDistrictDataSource = RemoteGeoRegionDistrictDataSourceImpl(
        ctx = ctx,
        regionDistrictService = regionDistrictService
    )

    @Singleton
    @Provides
    fun provideRemoteGeoLocalityDataSource(localityService: LocalityService): RemoteGeoLocalityDataSource =
        RemoteGeoLocalityDataSourceImpl(localityService = localityService)

    @Singleton
    @Provides
    fun provideRemoteGeoLocalityDistrictDataSource(
        @ApplicationContext ctx: Context, localityDistrictService: LocalityDistrictService
    ): RemoteGeoLocalityDistrictDataSource = RemoteGeoLocalityDistrictDataSourceImpl(
        ctx = ctx,
        localityDistrictService = localityDistrictService
    )

    @Singleton
    @Provides
    fun provideRemoteGeoMicrodistrictDataSource(microdistrictService: MicrodistrictService): RemoteGeoMicrodistrictDataSource =
        RemoteGeoMicrodistrictDataSourceImpl(microdistrictService = microdistrictService)

    @Singleton
    @Provides
    fun provideRemoteGeoStreetDataSource(streetService: StreetService): RemoteGeoStreetDataSource =
        RemoteGeoStreetDataSourceImpl(streetService = streetService)
 */
}