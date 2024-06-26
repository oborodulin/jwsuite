package com.oborodulin.jwsuite.presentation_geo.di

import android.content.Context
import com.oborodulin.jwsuite.domain.usecases.geocountry.CountryUseCases
import com.oborodulin.jwsuite.domain.usecases.geocountry.DeleteCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountriesUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.SaveCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.DeleteLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetAllLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.DeleteLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.LocalityDistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.SaveLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.DeleteMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.MicrodistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.SaveMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.RegionDistrictUseCases
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
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.CountriesListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.CountryConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalitiesListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalityConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalityDistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalityDistrictsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.MicrodistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.MicrodistrictsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionDistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionDistrictsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.SaveCountryConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.SaveRegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetLocalityDistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetMicrodistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetsForTerritoryListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.CoordinatesUiToGeoCoordinatesMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountriesListToCountriesListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountriesListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountryUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryUiToCountryMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalitiesListToLocalityListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalitiesListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictsListToLocalityDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictsListToMicrodistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionsListToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictUiToRegionDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictsListToRegionDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetsListToStreetsListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoModule {
    // MAPPERS:
    // Coordinates:
    @Singleton
    @Provides
    fun provideGeoCoordinatesToCoordinatesUiMapper(): GeoCoordinatesToCoordinatesUiMapper =
        GeoCoordinatesToCoordinatesUiMapper()

    @Singleton
    @Provides
    fun provideCoordinatesUiToGeoCoordinatesMapper(): CoordinatesUiToGeoCoordinatesMapper =
        CoordinatesUiToGeoCoordinatesMapper()

    // Countries:
    @Singleton
    @Provides
    fun provideCountryToCountryUiMapper(mapper: GeoCoordinatesToCoordinatesUiMapper): CountryToCountryUiMapper =
        CountryToCountryUiMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCountryUiToCountryMapper(mapper: CoordinatesUiToGeoCoordinatesMapper): CountryUiToCountryMapper =
        CountryUiToCountryMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCountryToCountriesListItemMapper(): CountryToCountriesListItemMapper =
        CountryToCountriesListItemMapper()

    @Singleton
    @Provides
    fun provideCountriesListToCountriesListItemMapper(mapper: CountryToCountriesListItemMapper): CountriesListToCountriesListItemMapper =
        CountriesListToCountriesListItemMapper(mapper = mapper)

    // Regions:
    @Singleton
    @Provides
    fun provideRegionToRegionUiMapper(
        countryMapper: CountryToCountryUiMapper,
        coordinatesMapper: GeoCoordinatesToCoordinatesUiMapper
    ): RegionToRegionUiMapper = RegionToRegionUiMapper(
        countryMapper = countryMapper, coordinatesMapper = coordinatesMapper
    )

    @Singleton
    @Provides
    fun provideRegionUiToRegionMapper(
        countryUiMapper: CountryUiToCountryMapper,
        coordinatesUiMapper: CoordinatesUiToGeoCoordinatesMapper
    ): RegionUiToRegionMapper = RegionUiToRegionMapper(
        countryUiMapper = countryUiMapper, coordinatesUiMapper = coordinatesUiMapper
    )

    @Singleton
    @Provides
    fun provideRegionToRegionsListItemMapper(): RegionToRegionsListItemMapper =
        RegionToRegionsListItemMapper()

    @Singleton
    @Provides
    fun provideRegionsListToRegionsListItemMapper(mapper: RegionToRegionsListItemMapper): RegionsListToRegionsListItemMapper =
        RegionsListToRegionsListItemMapper(mapper = mapper)

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictToRegionDistrictUiMapper(
        regionMapper: RegionToRegionUiMapper,
        coordinatesMapper: GeoCoordinatesToCoordinatesUiMapper
    ): RegionDistrictToRegionDistrictUiMapper = RegionDistrictToRegionDistrictUiMapper(
        regionMapper = regionMapper, coordinatesMapper = coordinatesMapper
    )

    @Singleton
    @Provides
    fun provideRegionDistrictUiToRegionDistrictMapper(
        regionUiMapper: RegionUiToRegionMapper,
        coordinatesUiMapper: CoordinatesUiToGeoCoordinatesMapper
    ): RegionDistrictUiToRegionDistrictMapper = RegionDistrictUiToRegionDistrictMapper(
        regionUiMapper = regionUiMapper, coordinatesUiMapper = coordinatesUiMapper
    )

    @Singleton
    @Provides
    fun provideRegionDistrictToRegionDistrictsListItemMapper(): RegionDistrictToRegionDistrictsListItemMapper =
        RegionDistrictToRegionDistrictsListItemMapper()

    @Singleton
    @Provides
    fun provideRegionDistrictsListToRegionDistrictsListItemMapper(mapper: RegionDistrictToRegionDistrictsListItemMapper): RegionDistrictsListToRegionDistrictsListItemMapper =
        RegionDistrictsListToRegionDistrictsListItemMapper(mapper = mapper)

    // Localities:
    @Singleton
    @Provides
    fun provideLocalityToLocalityUiMapper(
        regionMapper: RegionToRegionUiMapper,
        regionDistrictMapper: RegionDistrictToRegionDistrictUiMapper
    ): LocalityToLocalityUiMapper = LocalityToLocalityUiMapper(
        regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper
    )

    @Singleton
    @Provides
    fun provideLocalityUiToLocalityMapper(
        @ApplicationContext ctx: Context,
        regionUiMapper: RegionUiToRegionMapper,
        regionUiDistrictMapper: RegionDistrictUiToRegionDistrictMapper
    ): LocalityUiToLocalityMapper = LocalityUiToLocalityMapper(
        ctx = ctx, regionUiMapper = regionUiMapper, regionUiDistrictMapper = regionUiDistrictMapper
    )

    @Singleton
    @Provides
    fun provideLocalityToLocalitiesListItemMapper(): LocalityToLocalitiesListItemMapper =
        LocalityToLocalitiesListItemMapper()

    @Singleton
    @Provides
    fun provideLocalitiesListToLocalityListItemMapper(mapper: LocalityToLocalitiesListItemMapper): LocalitiesListToLocalityListItemMapper =
        LocalitiesListToLocalityListItemMapper(mapper = mapper)

    // LocalityDistricts:
    @Singleton
    @Provides
    fun provideLocalityDistrictToLocalityDistrictUiMapper(mapper: LocalityToLocalityUiMapper): LocalityDistrictToLocalityDistrictUiMapper =
        LocalityDistrictToLocalityDistrictUiMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityDistrictUiToLocalityDistrictMapper(mapper: LocalityUiToLocalityMapper): LocalityDistrictUiToLocalityDistrictMapper =
        LocalityDistrictUiToLocalityDistrictMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityDistrictToLocalityDistrictsListItemMapper(): LocalityDistrictToLocalityDistrictsListItemMapper =
        LocalityDistrictToLocalityDistrictsListItemMapper()

    @Singleton
    @Provides
    fun provideLocalityDistrictsListToLocalityDistrictsListItemMapper(mapper: LocalityDistrictToLocalityDistrictsListItemMapper): LocalityDistrictsListToLocalityDistrictsListItemMapper =
        LocalityDistrictsListToLocalityDistrictsListItemMapper(mapper = mapper)

    // Microdistricts:
    @Singleton
    @Provides
    fun provideMicrodistrictToMicrodistrictUiMapper(
        localityMapper: LocalityToLocalityUiMapper,
        localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper
    ): MicrodistrictToMicrodistrictUiMapper = MicrodistrictToMicrodistrictUiMapper(
        localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper
    )

    @Singleton
    @Provides
    fun provideMicrodistrictUiToMicrodistrictMapper(
        @ApplicationContext ctx: Context,
        localityUiMapper: LocalityUiToLocalityMapper,
        localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper
    ): MicrodistrictUiToMicrodistrictMapper = MicrodistrictUiToMicrodistrictMapper(
        ctx = ctx, localityUiMapper = localityUiMapper,
        localityDistrictUiMapper = localityDistrictUiMapper
    )

    @Singleton
    @Provides
    fun provideMicrodistrictToMicrodistrictsListItemMapper(): MicrodistrictToMicrodistrictsListItemMapper =
        MicrodistrictToMicrodistrictsListItemMapper()

    @Singleton
    @Provides
    fun provideMicrodistrictsListToMicrodistrictListItemMapper(mapper: MicrodistrictToMicrodistrictsListItemMapper): MicrodistrictsListToMicrodistrictsListItemMapper =
        MicrodistrictsListToMicrodistrictsListItemMapper(mapper = mapper)

    // Streets:
    @Singleton
    @Provides
    fun provideStreetToStreetUiMapper(
        localityMapper: LocalityToLocalityUiMapper,
        localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
        microdistrictMapper: MicrodistrictToMicrodistrictUiMapper
    ): StreetToStreetUiMapper = StreetToStreetUiMapper(
        localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
    )

    @Singleton
    @Provides
    fun provideStreetUiToStreetMapper(
        @ApplicationContext ctx: Context,
        localityUiMapper: LocalityUiToLocalityMapper,
        localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
        microdistrictUiMapper: MicrodistrictUiToMicrodistrictMapper
    ): StreetUiToStreetMapper = StreetUiToStreetMapper(
        ctx = ctx, localityUiMapper = localityUiMapper,
        localityDistrictUiMapper = localityDistrictUiMapper,
        microdistrictUiMapper = microdistrictUiMapper
    )

    @Singleton
    @Provides
    fun provideStreetToStreetsListItemMapper(): StreetToStreetsListItemMapper =
        StreetToStreetsListItemMapper()

    @Singleton
    @Provides
    fun provideStreetsListToStreetListItemMapper(mapper: StreetToStreetsListItemMapper): StreetsListToStreetsListItemMapper =
        StreetsListToStreetsListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Countries:
    @Singleton
    @Provides
    fun provideCountriesListConverter(mapper: CountriesListToCountriesListItemMapper): CountriesListConverter =
        CountriesListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideCountryConverter(mapper: CountryToCountryUiMapper): CountryConverter =
        CountryConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideSaveCountryConverter(mapper: CountryToCountryUiMapper): SaveCountryConverter =
        SaveCountryConverter(mapper = mapper)

    // Regions:
    @Singleton
    @Provides
    fun provideRegionsListConverter(mapper: RegionsListToRegionsListItemMapper): RegionsListConverter =
        RegionsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionConverter(mapper: RegionToRegionUiMapper): RegionConverter =
        RegionConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideSaveRegionConverter(mapper: RegionToRegionUiMapper): SaveRegionConverter =
        SaveRegionConverter(mapper = mapper)

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictsListConverter(mapper: RegionDistrictsListToRegionDistrictsListItemMapper): RegionDistrictsListConverter =
        RegionDistrictsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictConverter(mapper: RegionDistrictToRegionDistrictUiMapper): RegionDistrictConverter =
        RegionDistrictConverter(mapper = mapper)

    // Localities:
    @Singleton
    @Provides
    fun provideLocalitiesListConverter(mapper: LocalitiesListToLocalityListItemMapper): LocalitiesListConverter =
        LocalitiesListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityConverter(mapper: LocalityToLocalityUiMapper): LocalityConverter =
        LocalityConverter(mapper = mapper)

    // LocalityDistricts:
    @Singleton
    @Provides
    fun provideLocalityDistrictsListConverter(mapper: LocalityDistrictsListToLocalityDistrictsListItemMapper): LocalityDistrictsListConverter =
        LocalityDistrictsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityDistrictConverter(mapper: LocalityDistrictToLocalityDistrictUiMapper): LocalityDistrictConverter =
        LocalityDistrictConverter(mapper = mapper)

    // Microdistricts:
    @Singleton
    @Provides
    fun provideMicrodistrictsListConverter(mapper: MicrodistrictsListToMicrodistrictsListItemMapper): MicrodistrictsListConverter =
        MicrodistrictsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideMicrodistrictConverter(mapper: MicrodistrictToMicrodistrictUiMapper): MicrodistrictConverter =
        MicrodistrictConverter(mapper = mapper)

    // Streets:
    @Singleton
    @Provides
    fun provideStreetsListConverter(mapper: StreetsListToStreetsListItemMapper): StreetsListConverter =
        StreetsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideStreetsForTerritoryListConverter(mapper: StreetsListToStreetsListItemMapper): StreetsForTerritoryListConverter =
        StreetsForTerritoryListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideStreetLocalityDistrictConverter(
        streetMapper: StreetToStreetUiMapper,
        localityDistrictsListMapper: LocalityDistrictsListToLocalityDistrictsListItemMapper
    ): StreetLocalityDistrictConverter = StreetLocalityDistrictConverter(
        streetMapper = streetMapper, localityDistrictsListMapper = localityDistrictsListMapper
    )

    @Singleton
    @Provides
    fun provideStreetMicrodistrictConverter(
        streetMapper: StreetToStreetUiMapper,
        microdistrictsListMapper: MicrodistrictsListToMicrodistrictsListItemMapper
    ): StreetMicrodistrictConverter = StreetMicrodistrictConverter(
        streetMapper = streetMapper, microdistrictsListMapper = microdistrictsListMapper
    )

    @Singleton
    @Provides
    fun provideStreetConverter(mapper: StreetToStreetUiMapper): StreetConverter =
        StreetConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideCountryUseCases(
        getCountriesUseCase: GetCountriesUseCase,
        getCountryUseCase: GetCountryUseCase,
        saveCountryUseCase: SaveCountryUseCase,
        deleteCountryUseCase: DeleteCountryUseCase
    ): CountryUseCases = CountryUseCases(
        getCountriesUseCase,
        getCountryUseCase,
        saveCountryUseCase,
        deleteCountryUseCase
    )
    @Singleton
    @Provides
    fun provideRegionUseCases(
        getRegionsUseCase: GetRegionsUseCase,
        getRegionUseCase: GetRegionUseCase,
        saveRegionUseCase: SaveRegionUseCase,
        deleteRegionUseCase: DeleteRegionUseCase
    ): RegionUseCases = RegionUseCases(
        getRegionsUseCase,
        getRegionUseCase,
        saveRegionUseCase,
        deleteRegionUseCase
    )

    @Singleton
    @Provides
    fun provideRegionDistrictUseCases(
        getRegionDistrictsUseCase: GetRegionDistrictsUseCase,
        getRegionDistrictUseCase: GetRegionDistrictUseCase,
        saveRegionDistrictUseCase: SaveRegionDistrictUseCase,
        deleteRegionDistrictUseCase: DeleteRegionDistrictUseCase
    ): RegionDistrictUseCases = RegionDistrictUseCases(
        getRegionDistrictsUseCase,
        getRegionDistrictUseCase,
        saveRegionDistrictUseCase,
        deleteRegionDistrictUseCase
    )

    @Singleton
    @Provides
    fun provideLocalityUseCases(
        getLocalitiesUseCase: GetLocalitiesUseCase,
        getAllLocalitiesUseCase: GetAllLocalitiesUseCase,
        getLocalityUseCase: GetLocalityUseCase,
        saveLocalityUseCase: SaveLocalityUseCase,
        deleteLocalityUseCase: DeleteLocalityUseCase
    ): LocalityUseCases = LocalityUseCases(
        getLocalitiesUseCase,
        getAllLocalitiesUseCase,
        getLocalityUseCase,
        saveLocalityUseCase,
        deleteLocalityUseCase
    )

    @Singleton
    @Provides
    fun provideLocalityDistrictUseCases(
        getLocalityDistrictsUseCase: GetLocalityDistrictsUseCase,
        getLocalityDistrictUseCase: GetLocalityDistrictUseCase,
        saveLocalityDistrictUseCase: SaveLocalityDistrictUseCase,
        deleteLocalityDistrictUseCase: DeleteLocalityDistrictUseCase
    ): LocalityDistrictUseCases = LocalityDistrictUseCases(
        getLocalityDistrictsUseCase,
        getLocalityDistrictUseCase,
        saveLocalityDistrictUseCase,
        deleteLocalityDistrictUseCase
    )

    @Singleton
    @Provides
    fun provideMicrodistrictUseCases(
        getMicrodistrictsUseCase: GetMicrodistrictsUseCase,
        getMicrodistrictUseCase: GetMicrodistrictUseCase,
        saveMicrodistrictUseCase: SaveMicrodistrictUseCase,
        deleteMicrodistrictUseCase: DeleteMicrodistrictUseCase
    ): MicrodistrictUseCases = MicrodistrictUseCases(
        getMicrodistrictsUseCase,
        getMicrodistrictUseCase,
        saveMicrodistrictUseCase,
        deleteMicrodistrictUseCase
    )

    @Singleton
    @Provides
    fun provideStreetUseCases(
        getStreetsUseCase: GetStreetsUseCase,
        getStreetUseCase: GetStreetUseCase,
        saveStreetUseCase: SaveStreetUseCase,
        deleteStreetUseCase: DeleteStreetUseCase,
        deleteStreetLocalityDistrictUseCase: DeleteStreetLocalityDistrictUseCase,
        deleteStreetMicrodistrictUseCase: DeleteStreetMicrodistrictUseCase,
        getStreetsForTerritoryUseCase: GetStreetsForTerritoryUseCase,
        getLocalityDistrictsForStreetUseCase: GetLocalityDistrictsForStreetUseCase,
        getMicrodistrictsForStreetUseCase: GetMicrodistrictsForStreetUseCase,
        saveStreetLocalityDistrictsUseCase: SaveStreetLocalityDistrictsUseCase,
        saveStreetMicrodistrictsUseCase: SaveStreetMicrodistrictsUseCase
    ): StreetUseCases = StreetUseCases(
        getStreetsUseCase,
        getStreetUseCase,
        saveStreetUseCase,
        deleteStreetUseCase,
        deleteStreetLocalityDistrictUseCase,
        deleteStreetMicrodistrictUseCase,
        getStreetsForTerritoryUseCase,
        getLocalityDistrictsForStreetUseCase,
        getMicrodistrictsForStreetUseCase,
        saveStreetLocalityDistrictsUseCase,
        saveStreetMicrodistrictsUseCase
    )
}