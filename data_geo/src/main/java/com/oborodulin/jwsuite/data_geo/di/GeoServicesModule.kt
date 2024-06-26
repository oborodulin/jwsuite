package com.oborodulin.jwsuite.data_geo.di

import com.oborodulin.jwsuite.data_geo.remote.osm.service.CountryService
import com.oborodulin.jwsuite.data_geo.remote.osm.service.RegionService
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
/*
    @Singleton
    @Provides
    fun provideRegionDistrictService(retrofit: Retrofit): RegionDistrictService =
        retrofit.create(RegionDistrictService::class.java)

    @Singleton
    @Provides
    fun provideLocalityService(retrofit: Retrofit): LocalityService =
        retrofit.create(LocalityService::class.java)

    @Singleton
    @Provides
    fun provideLocalityDistrictService(retrofit: Retrofit): LocalityDistrictService =
        retrofit.create(LocalityDistrictService::class.java)

    @Singleton
    @Provides
    fun provideMicrodistrictService(retrofit: Retrofit): MicrodistrictService =
        retrofit.create(MicrodistrictService::class.java)

    @Singleton
    @Provides
    fun provideStreetService(retrofit: Retrofit): StreetService =
        retrofit.create(StreetService::class.java)
 */
}