package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.geolocality.DeleteLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.SaveRegionDistrictUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoUseCasesModule {
    // Region:
    @Singleton
    @Provides
    fun provideGetRegionUseCase(
        configuration: UseCase.Configuration, regionsRepository: GeoRegionsRepository
    ): GetRegionUseCase = GetRegionUseCase(configuration, regionsRepository)

    @Singleton
    @Provides
    fun provideGetRegionsUseCase(
        configuration: UseCase.Configuration, regionsRepository: GeoRegionsRepository
    ): GetRegionsUseCase = GetRegionsUseCase(configuration, regionsRepository)

    @Singleton
    @Provides
    fun provideDeleteRegionUseCase(
        configuration: UseCase.Configuration, regionsRepository: GeoRegionsRepository
    ): DeleteRegionUseCase = DeleteRegionUseCase(configuration, regionsRepository)

    @Singleton
    @Provides
    fun provideSaveRegionUseCase(
        configuration: UseCase.Configuration, regionsRepository: GeoRegionsRepository
    ): SaveRegionUseCase = SaveRegionUseCase(configuration, regionsRepository)

    // RegionDistrict:
    @Singleton
    @Provides
    fun provideGetRegionDistrictUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoRegionDistrictsRepository
    ): GetRegionDistrictUseCase = GetRegionDistrictUseCase(configuration, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideGetRegionDistrictsUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoRegionDistrictsRepository
    ): GetRegionDistrictsUseCase =
        GetRegionDistrictsUseCase(configuration, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideDeleteRegionDistrictUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoRegionDistrictsRepository
    ): DeleteRegionDistrictUseCase =
        DeleteRegionDistrictUseCase(configuration, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideSaveRegionDistrictUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoRegionDistrictsRepository
    ): SaveRegionDistrictUseCase =
        SaveRegionDistrictUseCase(configuration, regionDistrictsRepository)

    // Locality:
    @Singleton
    @Provides
    fun provideGetLocalityUseCase(
        configuration: UseCase.Configuration, localitiesRepository: GeoLocalitiesRepository
    ): GetLocalityUseCase = GetLocalityUseCase(configuration, localitiesRepository)

    @Singleton
    @Provides
    fun provideGetLocalitiesUseCase(
        configuration: UseCase.Configuration, localitiesRepository: GeoLocalitiesRepository
    ): GetLocalitiesUseCase = GetLocalitiesUseCase(configuration, localitiesRepository)

    @Singleton
    @Provides
    fun provideDeleteLocalityUseCase(
        configuration: UseCase.Configuration, localitiesRepository: GeoLocalitiesRepository
    ): DeleteLocalityUseCase = DeleteLocalityUseCase(configuration, localitiesRepository)

    @Singleton
    @Provides
    fun provideSaveLocalityUseCase(
        configuration: UseCase.Configuration, localitiesRepository: GeoLocalitiesRepository
    ): SaveLocalityUseCase = SaveLocalityUseCase(configuration, localitiesRepository)

}