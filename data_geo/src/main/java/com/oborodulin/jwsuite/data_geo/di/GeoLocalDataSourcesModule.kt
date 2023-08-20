package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoLocalityDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoLocalityDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoMicrodistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoRegionDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoRegionDistrictDataSource
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoLocalityDataSourceImpl
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoLocalityDistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoMicrodistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoRegionDataSourceImpl
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoRegionDistrictDataSourceImpl
import com.oborodulin.jwsuite.data_geo.local.db.sources.local.LocalGeoStreetDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoLocalDataSourcesModule {
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
}