package com.oborodulin.jwsuite.data_geo.di

import android.content.Context
import com.oborodulin.jwsuite.data_geo.remote.osm.model.country.CountryService
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionService
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
interface GeoRemoteDataSourcesModule {
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
}