package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoCountryDataSource
import com.oborodulin.jwsuite.data_geo.remote.sources.RemoteGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.sources.remote.RemoteGeoCountryDataSourceImpl
import com.oborodulin.jwsuite.data_geo.sources.remote.RemoteGeoRegionDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeoRemoteDataSourcesModule {
    @Binds
    abstract fun bindRemoteGeoCountryDataSource(dataSourceImpl: RemoteGeoCountryDataSourceImpl): RemoteGeoCountryDataSource

    @Binds
    abstract fun bindRemoteGeoRegionDataSource(dataSourceImpl: RemoteGeoRegionDataSourceImpl): RemoteGeoRegionDataSource

    /*    @Singleton
        @Provides
        fun provideRemoteGeoCountryDataSource(
            countryDao: GeoCountryDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): RemoteGeoCountryDataSource = RemoteGeoCountryDataSourceImpl(countryDao, dispatcher)

        @Singleton
        @Provides
        fun provideLocalGeoRegionDataSource(
            regionDao: GeoRegionDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalGeoRegionDataSource = LocalGeoRegionDataSourceImpl(regionDao, dispatcher)
     */
}