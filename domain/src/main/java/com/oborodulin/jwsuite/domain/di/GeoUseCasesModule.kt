package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.geocountry.DeleteCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountriesUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.SaveCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.DeleteLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetAllLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.DeleteLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.SaveLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.DeleteMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.SaveMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.SaveRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetLocalityDistrictsForStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetMicrodistrictsForStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetMicrodistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoUseCasesModule {
    // Country:
    @Singleton
    @Provides
    fun provideGetCountryUseCase(
        configuration: UseCase.Configuration, countriesRepository: GeoCountriesRepository
    ): GetCountryUseCase = GetCountryUseCase(configuration, countriesRepository)

    @Singleton
    @Provides
    fun provideGetCountriesUseCase(
        configuration: UseCase.Configuration, countriesRepository: GeoCountriesRepository
    ): GetCountriesUseCase = GetCountriesUseCase(configuration, countriesRepository)

    @Singleton
    @Provides
    fun provideDeleteCountryUseCase(
        configuration: UseCase.Configuration, countriesRepository: GeoCountriesRepository
    ): DeleteCountryUseCase = DeleteCountryUseCase(configuration, countriesRepository)

    @Singleton
    @Provides
    fun provideSaveCountryUseCase(
        configuration: UseCase.Configuration, countriesRepository: GeoCountriesRepository
    ): SaveCountryUseCase = SaveCountryUseCase(configuration, countriesRepository)

    // Region:
    @Singleton
    @Provides
    fun provideGetRegionUseCase(
        configuration: UseCase.Configuration,
        countriesRepository: GeoCountriesRepository,
        regionsRepository: GeoRegionsRepository
    ): GetRegionUseCase = GetRegionUseCase(configuration, countriesRepository, regionsRepository)

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
        countriesRepository: GeoCountriesRepository,
        regionDistrictsRepository: GeoRegionDistrictsRepository
    ): GetRegionDistrictUseCase =
        GetRegionDistrictUseCase(configuration, countriesRepository, regionDistrictsRepository)

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
    fun provideGetAllLocalitiesUseCase(
        configuration: UseCase.Configuration, localitiesRepository: GeoLocalitiesRepository
    ): GetAllLocalitiesUseCase = GetAllLocalitiesUseCase(configuration, localitiesRepository)

    @Singleton
    @Provides
    fun provideGetLocalityUseCase(
        configuration: UseCase.Configuration,
        countriesRepository: GeoCountriesRepository,
        localitiesRepository: GeoLocalitiesRepository
    ): GetLocalityUseCase =
        GetLocalityUseCase(configuration, countriesRepository, localitiesRepository)

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

    // LocalityDistrict:
    @Singleton
    @Provides
    fun provideGetLocalityDistrictUseCase(
        configuration: UseCase.Configuration,
        countriesRepository: GeoCountriesRepository,
        regionDistrictsRepository: GeoLocalityDistrictsRepository
    ): GetLocalityDistrictUseCase =
        GetLocalityDistrictUseCase(configuration, countriesRepository, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideGetLocalityDistrictsUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoLocalityDistrictsRepository
    ): GetLocalityDistrictsUseCase =
        GetLocalityDistrictsUseCase(configuration, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideDeleteLocalityDistrictUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoLocalityDistrictsRepository
    ): DeleteLocalityDistrictUseCase =
        DeleteLocalityDistrictUseCase(configuration, regionDistrictsRepository)

    @Singleton
    @Provides
    fun provideSaveLocalityDistrictUseCase(
        configuration: UseCase.Configuration,
        regionDistrictsRepository: GeoLocalityDistrictsRepository
    ): SaveLocalityDistrictUseCase =
        SaveLocalityDistrictUseCase(configuration, regionDistrictsRepository)

    // Microdistrict:
    @Singleton
    @Provides
    fun provideGetMicrodistrictUseCase(
        configuration: UseCase.Configuration,
        countriesRepository: GeoCountriesRepository,
        microdistrictsRepository: GeoMicrodistrictsRepository
    ): GetMicrodistrictUseCase =
        GetMicrodistrictUseCase(configuration, countriesRepository, microdistrictsRepository)

    @Singleton
    @Provides
    fun provideGetMicrodistrictsUseCase(
        configuration: UseCase.Configuration, microdistrictsRepository: GeoMicrodistrictsRepository
    ): GetMicrodistrictsUseCase = GetMicrodistrictsUseCase(configuration, microdistrictsRepository)

    @Singleton
    @Provides
    fun provideDeleteMicrodistrictUseCase(
        configuration: UseCase.Configuration, microdistrictsRepository: GeoMicrodistrictsRepository
    ): DeleteMicrodistrictUseCase =
        DeleteMicrodistrictUseCase(configuration, microdistrictsRepository)

    @Singleton
    @Provides
    fun provideSaveMicrodistrictUseCase(
        configuration: UseCase.Configuration, microdistrictsRepository: GeoMicrodistrictsRepository
    ): SaveMicrodistrictUseCase = SaveMicrodistrictUseCase(configuration, microdistrictsRepository)

    // Street:
    @Singleton
    @Provides
    fun provideGetStreetUseCase(
        configuration: UseCase.Configuration,
        countriesRepository: GeoCountriesRepository,
        streetsRepository: GeoStreetsRepository
    ): GetStreetUseCase = GetStreetUseCase(configuration, countriesRepository, streetsRepository)

    @Singleton
    @Provides
    fun provideGetStreetsUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): GetStreetsUseCase = GetStreetsUseCase(configuration, streetsRepository)

    @Singleton
    @Provides
    fun provideDeleteStreetUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): DeleteStreetUseCase = DeleteStreetUseCase(configuration, streetsRepository)

    @Singleton
    @Provides
    fun provideDeleteStreetLocalityDistrictUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): DeleteStreetLocalityDistrictUseCase =
        DeleteStreetLocalityDistrictUseCase(configuration, streetsRepository)

    @Singleton
    @Provides
    fun provideDeleteStreetMicrodistrictUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): DeleteStreetMicrodistrictUseCase =
        DeleteStreetMicrodistrictUseCase(configuration, streetsRepository)

    @Singleton
    @Provides
    fun provideSaveStreetUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): SaveStreetUseCase = SaveStreetUseCase(configuration, streetsRepository)

    @Singleton
    @Provides
    fun provideSaveStreetLocalityDistrictsUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): SaveStreetLocalityDistrictsUseCase = SaveStreetLocalityDistrictsUseCase(
        configuration, streetsRepository
    )

    @Singleton
    @Provides
    fun provideSaveStreetMicrodistrictsUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): SaveStreetMicrodistrictsUseCase = SaveStreetMicrodistrictsUseCase(
        configuration, streetsRepository
    )

    @Singleton
    @Provides
    fun provideGetStreetsForTerritoryUseCase(
        configuration: UseCase.Configuration, streetsRepository: GeoStreetsRepository
    ): GetStreetsForTerritoryUseCase = GetStreetsForTerritoryUseCase(
        configuration, streetsRepository
    )

    @Singleton
    @Provides
    fun provideGetLocalityDistrictsForStreetUseCase(
        configuration: UseCase.Configuration,
        streetsRepository: GeoStreetsRepository,
        localityDistrictsRepository: GeoLocalityDistrictsRepository
    ): GetLocalityDistrictsForStreetUseCase = GetLocalityDistrictsForStreetUseCase(
        configuration, streetsRepository, localityDistrictsRepository
    )

    @Singleton
    @Provides
    fun provideGetMicrodistrictsForStreetUseCase(
        configuration: UseCase.Configuration,
        streetsRepository: GeoStreetsRepository,
        microdistrictsRepository: GeoMicrodistrictsRepository
    ): GetMicrodistrictsForStreetUseCase = GetMicrodistrictsForStreetUseCase(
        configuration, streetsRepository, microdistrictsRepository
    )
}