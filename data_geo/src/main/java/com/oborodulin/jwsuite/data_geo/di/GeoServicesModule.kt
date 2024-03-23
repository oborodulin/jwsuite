package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.remote.osm.model.country.CountryService
import com.oborodulin.jwsuite.data_geo.remote.osm.model.region.RegionService
import com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict.RegionDistrictService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoServicesModule {
    @Singleton //@ViewModelScoped
    @Provides
    fun provideCountryService(retrofit: Retrofit): CountryService =
        retrofit.create(CountryService::class.java)

    @Singleton
    @Provides
    fun provideRegionService(retrofit: Retrofit): RegionService =
        retrofit.create(RegionService::class.java)

    @Singleton
    @Provides
    fun provideRegionDistrictService(retrofit: Retrofit): RegionDistrictService =
        retrofit.create(RegionDistrictService::class.java)
}