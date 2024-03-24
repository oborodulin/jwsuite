package com.oborodulin.jwsuite.data_geo.di

import android.content.Context
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryCsvListToGeoCountryEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryCsvToGeoCountryEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryEntityListToGeoCountryCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.GeoCountryEntityToGeoCountryCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl.GeoCountryTlCsvListToGeoCountryTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl.GeoCountryTlCsvToGeoCountryTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl.GeoCountryTlEntityListToGeoCountryTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geocountry.tl.GeoCountryTlEntityToGeoCountryTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvListToGeoLocalityEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityCsvToGeoLocalityEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityEntityListToGeoLocalityCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityEntityToGeoLocalityCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl.GeoLocalityTlCsvListToGeoLocalityTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl.GeoLocalityTlCsvToGeoLocalityTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl.GeoLocalityTlEntityListToGeoLocalityTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.tl.GeoLocalityTlEntityToGeoLocalityTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.GeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl.GeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl.GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl.GeoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocalitydistrict.tl.GeoLocalityDistrictTlEntityToGeoLocalityDistrictTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictCsvToGeoMicrodistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.GeoMicrodistrictEntityToGeoMicrodistrictCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl.GeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl.GeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl.GeoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict.tl.GeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvListToGeoRegionEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionCsvToGeoRegionEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionEntityListToGeoRegionCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.GeoRegionEntityToGeoRegionCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl.GeoRegionTlCsvListToGeoRegionTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl.GeoRegionTlCsvToGeoRegionTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl.GeoRegionTlEntityListToGeoRegionTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl.GeoRegionTlEntityToGeoRegionTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictCsvToGeoRegionDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl.GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvListToGeoStreetEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetCsvToGeoStreetEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetEntityListToGeoStreetCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.GeoStreetEntityToGeoStreetCsvMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl.GeoStreetTlCsvListToGeoStreetTlEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl.GeoStreetTlCsvToGeoStreetTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl.GeoStreetTlEntityListToGeoStreetTlCsvListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet.tl.GeoStreetTlEntityToGeoStreetTlCsvMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountriesListToGeoCountryEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryToGeoCountryEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryToGeoCountryTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewListToGeoCountriesListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry.GeoCountryViewToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityToGeoLocalityTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewListToGeoLocalitiesListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewListToGeoLocalityDistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewListToGeoMicrodistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionToGeoRegionEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionToGeoRegionTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionsListToGeoRegionEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewListToGeoRegionsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewListToGeoRegionDistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetToGeoStreetEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetToGeoStreetTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetsListToGeoStreetEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry.CountryElementToGeoCountryMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry.CountryElementsListToGeoCountriesListMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geocountry.GeoCountryApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality.GeoLocalityApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality.LocalityElementToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality.LocalityElementsListToGeoLocalitiesListMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion.GeoRegionApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion.RegionElementToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion.RegionElementsListToGeoRegionsListMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict.GeoRegionDistrictApiMappers
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict.RegionDistrictElementToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict.RegionDistrictElementsListToGeoRegionDistrictsListMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoMappersModule {
    // MAPPERS:
    // Coordinates:
    @Singleton
    @Provides
    fun provideCoordinatesToGeoCoordinatesMapper(): CoordinatesToGeoCoordinatesMapper =
        CoordinatesToGeoCoordinatesMapper()

    @Singleton
    @Provides
    fun provideGeoCoordinatesToCoordinatesMapper(): GeoCoordinatesToCoordinatesMapper =
        GeoCoordinatesToCoordinatesMapper()

    // Countries:
    @Singleton
    @Provides
    fun provideGeoCountryViewToGeoCountryMapper(mapper: CoordinatesToGeoCoordinatesMapper): GeoCountryViewToGeoCountryMapper =
        GeoCountryViewToGeoCountryMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryViewListToGeoCountriesListMapper(mapper: GeoCountryViewToGeoCountryMapper): GeoCountryViewListToGeoCountriesListMapper =
        GeoCountryViewListToGeoCountriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryToGeoCountryEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoCountryToGeoCountryEntityMapper =
        GeoCountryToGeoCountryEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryToGeoCountryTlEntityMapper(): GeoCountryToGeoCountryTlEntityMapper =
        GeoCountryToGeoCountryTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoCountriesListToGeoCountryEntityListMapper(mapper: GeoCountryToGeoCountryEntityMapper): GeoCountriesListToGeoCountryEntityListMapper =
        GeoCountriesListToGeoCountryEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryMappers(
        geoCountryViewListToGeoCountriesListMapper: GeoCountryViewListToGeoCountriesListMapper,
        geoCountryViewToGeoCountryMapper: GeoCountryViewToGeoCountryMapper,
        geoCountriesListToGeoCountryEntityListMapper: GeoCountriesListToGeoCountryEntityListMapper,
        geoCountryToGeoCountryEntityMapper: GeoCountryToGeoCountryEntityMapper,
        geoCountryToGeoCountryTlEntityMapper: GeoCountryToGeoCountryTlEntityMapper
    ): GeoCountryMappers = GeoCountryMappers(
        geoCountryViewListToGeoCountriesListMapper,
        geoCountryViewToGeoCountryMapper,
        geoCountriesListToGeoCountryEntityListMapper,
        geoCountryToGeoCountryEntityMapper,
        geoCountryToGeoCountryTlEntityMapper
    )

    // Regions:
    @Singleton
    @Provides
    fun provideRegionViewToGeoRegionMapper(
        @ApplicationContext ctx: Context,
        mapper: CoordinatesToGeoCoordinatesMapper
    ): RegionViewToGeoRegionMapper = RegionViewToGeoRegionMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionViewListToGeoRegionsListMapper(mapper: RegionViewToGeoRegionMapper): RegionViewListToGeoRegionsListMapper =
        RegionViewListToGeoRegionsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionViewToGeoRegionMapper(
        countryMapper: GeoCountryViewToGeoCountryMapper,
        coordinatesMapper: CoordinatesToGeoCoordinatesMapper
    ): GeoRegionViewToGeoRegionMapper = GeoRegionViewToGeoRegionMapper(
        countryMapper = countryMapper, coordinatesMapper = coordinatesMapper
    )

    @Singleton
    @Provides
    fun provideGeoRegionToGeoRegionEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoRegionToGeoRegionEntityMapper =
        GeoRegionToGeoRegionEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionToGeoRegionTlEntityMapper(): GeoRegionToGeoRegionTlEntityMapper =
        GeoRegionToGeoRegionTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionsListToGeoRegionEntityListMapper(mapper: GeoRegionToGeoRegionEntityMapper): GeoRegionsListToGeoRegionEntityListMapper =
        GeoRegionsListToGeoRegionEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionMappers(
        regionViewListToGeoRegionsListMapper: RegionViewListToGeoRegionsListMapper,
        geoRegionViewToGeoRegionMapper: GeoRegionViewToGeoRegionMapper,
        geoRegionsListToGeoRegionEntityListMapper: GeoRegionsListToGeoRegionEntityListMapper,
        geoRegionToGeoRegionEntityMapper: GeoRegionToGeoRegionEntityMapper,
        geoRegionToGeoRegionTlEntityMapper: GeoRegionToGeoRegionTlEntityMapper
    ): GeoRegionMappers = GeoRegionMappers(
        regionViewListToGeoRegionsListMapper,
        geoRegionViewToGeoRegionMapper,
        geoRegionsListToGeoRegionEntityListMapper,
        geoRegionToGeoRegionEntityMapper,
        geoRegionToGeoRegionTlEntityMapper
    )

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictViewToGeoRegionDistrictMapper(mapper: CoordinatesToGeoCoordinatesMapper): RegionDistrictViewToGeoRegionDistrictMapper =
        RegionDistrictViewToGeoRegionDistrictMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictViewListToGeoRegionDistrictsListMapper(mapper: RegionDistrictViewToGeoRegionDistrictMapper): RegionDistrictViewListToGeoRegionDistrictsListMapper =
        RegionDistrictViewListToGeoRegionDistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictViewToGeoRegionDistrictMapper(
        countryMapper: GeoCountryViewToGeoCountryMapper,
        regionMapper: RegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper
    ): GeoRegionDistrictViewToGeoRegionDistrictMapper =
        GeoRegionDistrictViewToGeoRegionDistrictMapper(
            countryMapper = countryMapper,
            regionMapper = regionMapper,
            regionDistrictMapper = regionDistrictMapper
        )

    @Singleton
    @Provides
    fun provideGeoRegionDistrictToGeoRegionDistrictEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoRegionDistrictToGeoRegionDistrictEntityMapper =
        GeoRegionDistrictToGeoRegionDistrictEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictToGeoRegionDistrictTlEntityMapper(): GeoRegionDistrictToGeoRegionDistrictTlEntityMapper =
        GeoRegionDistrictToGeoRegionDistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictsListToGeoRegionDistrictEntityListMapper(mapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper): GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper =
        GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictMappers(
        regionDistrictViewListToGeoRegionDistrictsListMapper: RegionDistrictViewListToGeoRegionDistrictsListMapper,
        geoRegionDistrictViewToGeoRegionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        geoRegionDistrictsListToGeoRegionDistrictEntityListMapper: GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictToGeoRegionDistrictEntityMapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper,
        geoRegionDistrictToGeoRegionDistrictTlEntityMapper: GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
    ): GeoRegionDistrictMappers = GeoRegionDistrictMappers(
        regionDistrictViewListToGeoRegionDistrictsListMapper,
        geoRegionDistrictViewToGeoRegionDistrictMapper,
        geoRegionDistrictsListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictToGeoRegionDistrictEntityMapper,
        geoRegionDistrictToGeoRegionDistrictTlEntityMapper
    )

    // Localities:
    @Singleton
    @Provides
    fun provideLocalityViewToGeoLocalityMapper(
        @ApplicationContext ctx: Context, mapper: CoordinatesToGeoCoordinatesMapper
    ): LocalityViewToGeoLocalityMapper =
        LocalityViewToGeoLocalityMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityViewListToGeoLocalitiesListMapper(mapper: LocalityViewToGeoLocalityMapper): LocalityViewListToGeoLocalitiesListMapper =
        LocalityViewListToGeoLocalitiesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityViewToGeoLocalityMapper(
        countryMapper: GeoCountryViewToGeoCountryMapper,
        regionMapper: RegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper
    ): GeoLocalityViewToGeoLocalityMapper = GeoLocalityViewToGeoLocalityMapper(
        countryMapper = countryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper
    )

    @Singleton
    @Provides
    fun provideGeoLocalityToGeoLocalityEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoLocalityToGeoLocalityEntityMapper =
        GeoLocalityToGeoLocalityEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityToGeoLocalityTlEntityMapper(): GeoLocalityToGeoLocalityTlEntityMapper =
        GeoLocalityToGeoLocalityTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalitiesListToGeoLocalityEntityListMapper(mapper: GeoLocalityToGeoLocalityEntityMapper): GeoLocalitiesListToGeoLocalityEntityListMapper =
        GeoLocalitiesListToGeoLocalityEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityMappers(
        localityViewListToGeoLocalitiesListMapper: LocalityViewListToGeoLocalitiesListMapper,
        geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
        geoLocalitiesListToGeoLocalityEntityListMapper: GeoLocalitiesListToGeoLocalityEntityListMapper,
        geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
        geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
    ): GeoLocalityMappers = GeoLocalityMappers(
        localityViewListToGeoLocalitiesListMapper,
        geoLocalityViewToGeoLocalityMapper,
        geoLocalitiesListToGeoLocalityEntityListMapper,
        geoLocalityToGeoLocalityEntityMapper,
        geoLocalityToGeoLocalityTlEntityMapper
    )

    // LocalityDistricts:
    @Singleton
    @Provides
    fun provideLocalityDistrictViewToGeoLocalityDistrictMapper(mapper: CoordinatesToGeoCoordinatesMapper): LocalityDistrictViewToGeoLocalityDistrictMapper =
        LocalityDistrictViewToGeoLocalityDistrictMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper: LocalityDistrictViewToGeoLocalityDistrictMapper): LocalityDistrictViewListToGeoLocalityDistrictsListMapper =
        LocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictViewToGeoLocalityDistrictMapper(
        countryMapper: GeoCountryViewToGeoCountryMapper,
        regionMapper: RegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
    ): GeoLocalityDistrictViewToGeoLocalityDistrictMapper =
        GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
            countryMapper = countryMapper,
            regionMapper = regionMapper,
            regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper,
            localityDistrictMapper = localityDistrictMapper
        )

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictToGeoLocalityDistrictEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoLocalityDistrictToGeoLocalityDistrictEntityMapper =
        GeoLocalityDistrictToGeoLocalityDistrictEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper(): GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper =
        GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper(mapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper): GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper =
        GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictMappers(
        localityDistrictViewListToGeoLocalityDistrictsListMapper: LocalityDistrictViewListToGeoLocalityDistrictsListMapper,
        geoLocalityDistrictViewToGeoLocalityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
        geoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper: GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictToGeoLocalityDistrictEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper,
        geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
    ): GeoLocalityDistrictMappers = GeoLocalityDistrictMappers(
        localityDistrictViewListToGeoLocalityDistrictsListMapper,
        geoLocalityDistrictViewToGeoLocalityDistrictMapper,
        geoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictToGeoLocalityDistrictEntityMapper,
        geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
    )

    // Microdistricts:
    @Singleton
    @Provides
    fun provideMicrodistrictViewToGeoMicrodistrictMapper(
        @ApplicationContext ctx: Context, mapper: CoordinatesToGeoCoordinatesMapper
    ): MicrodistrictViewToGeoMicrodistrictMapper =
        MicrodistrictViewToGeoMicrodistrictMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideMicrodistrictViewListToGeoMicrodistrictsListMapper(mapper: MicrodistrictViewToGeoMicrodistrictMapper): MicrodistrictViewListToGeoMicrodistrictsListMapper =
        MicrodistrictViewListToGeoMicrodistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictViewToGeoMicrodistrictMapper(
        countryMapper: GeoCountryViewToGeoCountryMapper,
        regionMapper: RegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
    ): GeoMicrodistrictViewToGeoMicrodistrictMapper = GeoMicrodistrictViewToGeoMicrodistrictMapper(
        countryMapper = countryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
    )

    @Singleton
    @Provides
    fun provideGeoMicrodistrictToGeoMicrodistrictEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoMicrodistrictToGeoMicrodistrictEntityMapper =
        GeoMicrodistrictToGeoMicrodistrictEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictToGeoMicrodistrictTlEntityMapper(): GeoMicrodistrictToGeoMicrodistrictTlEntityMapper =
        GeoMicrodistrictToGeoMicrodistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictsListToGeoMicrodistrictEntityListMapper(mapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper): GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper =
        GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictMappers(
        microdistrictViewListToGeoMicrodistrictsListMapper: MicrodistrictViewListToGeoMicrodistrictsListMapper,
        geoMicrodistrictViewToGeoMicrodistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper,
        geoMicrodistrictsListToGeoMicrodistrictEntityListMapper: GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictToGeoMicrodistrictEntityMapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper,
        geoMicrodistrictToGeoMicrodistrictTlEntityMapper: GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
    ): GeoMicrodistrictMappers = GeoMicrodistrictMappers(
        microdistrictViewListToGeoMicrodistrictsListMapper,
        geoMicrodistrictViewToGeoMicrodistrictMapper,
        geoMicrodistrictsListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictToGeoMicrodistrictEntityMapper,
        geoMicrodistrictToGeoMicrodistrictTlEntityMapper
    )

    // Streets:
    @Singleton
    @Provides
    fun provideStreetViewToGeoStreetMapper(
        @ApplicationContext ctx: Context, mapper: CoordinatesToGeoCoordinatesMapper
    ): StreetViewToGeoStreetMapper = StreetViewToGeoStreetMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideStreetViewListToGeoStreetsListMapper(mapper: StreetViewToGeoStreetMapper): StreetViewListToGeoStreetsListMapper =
        StreetViewListToGeoStreetsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetViewToGeoStreetMapper(
        localityMapper: GeoLocalityViewToGeoLocalityMapper,
        streetMapper: StreetViewToGeoStreetMapper
    ): GeoStreetViewToGeoStreetMapper = GeoStreetViewToGeoStreetMapper(
        localityMapper = localityMapper, streetMapper = streetMapper
    )

    @Singleton
    @Provides
    fun provideGeoStreetToGeoStreetEntityMapper(mapper: GeoCoordinatesToCoordinatesMapper): GeoStreetToGeoStreetEntityMapper =
        GeoStreetToGeoStreetEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetToGeoStreetTlEntityMapper(): GeoStreetToGeoStreetTlEntityMapper =
        GeoStreetToGeoStreetTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoStreetsListToGeoStreetEntityListMapper(mapper: GeoStreetToGeoStreetEntityMapper): GeoStreetsListToGeoStreetEntityListMapper =
        GeoStreetsListToGeoStreetEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetMappers(
        streetViewListToGeoStreetsListMapper: StreetViewListToGeoStreetsListMapper,
        geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
        geoStreetsListToGeoStreetEntityListMapper: GeoStreetsListToGeoStreetEntityListMapper,
        geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
        geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper
    ): GeoStreetMappers = GeoStreetMappers(
        streetViewListToGeoStreetsListMapper,
        geoStreetViewToGeoStreetMapper,
        geoStreetsListToGeoStreetEntityListMapper,
        geoStreetToGeoStreetEntityMapper,
        geoStreetToGeoStreetTlEntityMapper
    )

    // ------------------------------------------- CSV: -------------------------------------------
    // CountryCsv
    @Singleton
    @Provides
    fun provideGeoCountryEntityToGeoCountryCsvMapper(): GeoCountryEntityToGeoCountryCsvMapper =
        GeoCountryEntityToGeoCountryCsvMapper()

    @Singleton
    @Provides
    fun provideGeoCountryEntityListToGeoCountryCsvListMapper(mapper: GeoCountryEntityToGeoCountryCsvMapper): GeoCountryEntityListToGeoCountryCsvListMapper =
        GeoCountryEntityListToGeoCountryCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryCsvToGeoCountryEntityMapper(): GeoCountryCsvToGeoCountryEntityMapper =
        GeoCountryCsvToGeoCountryEntityMapper()

    @Singleton
    @Provides
    fun provideGeoCountryCsvListToGeoCountryEntityListMapper(mapper: GeoCountryCsvToGeoCountryEntityMapper): GeoCountryCsvListToGeoCountryEntityListMapper =
        GeoCountryCsvListToGeoCountryEntityListMapper(mapper = mapper)

    // CountryTlCsv
    @Singleton
    @Provides
    fun provideGeoCountryTlEntityToGeoCountryTlCsvMapper(): GeoCountryTlEntityToGeoCountryTlCsvMapper =
        GeoCountryTlEntityToGeoCountryTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoCountryTlEntityListToGeoCountryTlCsvListMapper(mapper: GeoCountryTlEntityToGeoCountryTlCsvMapper): GeoCountryTlEntityListToGeoCountryTlCsvListMapper =
        GeoCountryTlEntityListToGeoCountryTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryTlCsvToGeoCountryTlEntityMapper(): GeoCountryTlCsvToGeoCountryTlEntityMapper =
        GeoCountryTlCsvToGeoCountryTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoCountryTlCsvListToGeoCountryTlEntityListMapper(mapper: GeoCountryTlCsvToGeoCountryTlEntityMapper): GeoCountryTlCsvListToGeoCountryTlEntityListMapper =
        GeoCountryTlCsvListToGeoCountryTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryCsvMappers(
        geoCountryEntityListToGeoCountryCsvListMapper: GeoCountryEntityListToGeoCountryCsvListMapper,
        geoCountryCsvListToGeoCountryEntityListMapper: GeoCountryCsvListToGeoCountryEntityListMapper,
        geoCountryTlEntityListToGeoCountryTlCsvListMapper: GeoCountryTlEntityListToGeoCountryTlCsvListMapper,
        geoCountryTlCsvListToGeoCountryTlEntityListMapper: GeoCountryTlCsvListToGeoCountryTlEntityListMapper
    ): GeoCountryCsvMappers = GeoCountryCsvMappers(
        geoCountryEntityListToGeoCountryCsvListMapper,
        geoCountryCsvListToGeoCountryEntityListMapper,
        geoCountryTlEntityListToGeoCountryTlCsvListMapper,
        geoCountryTlCsvListToGeoCountryTlEntityListMapper
    )

    // RegionCsv
    @Singleton
    @Provides
    fun provideGeoRegionEntityToGeoRegionCsvMapper(): GeoRegionEntityToGeoRegionCsvMapper =
        GeoRegionEntityToGeoRegionCsvMapper()

    @Singleton
    @Provides
    fun provideGeoRegionEntityListToGeoRegionCsvListMapper(mapper: GeoRegionEntityToGeoRegionCsvMapper): GeoRegionEntityListToGeoRegionCsvListMapper =
        GeoRegionEntityListToGeoRegionCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionCsvToGeoRegionEntityMapper(): GeoRegionCsvToGeoRegionEntityMapper =
        GeoRegionCsvToGeoRegionEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionCsvListToGeoRegionEntityListMapper(mapper: GeoRegionCsvToGeoRegionEntityMapper): GeoRegionCsvListToGeoRegionEntityListMapper =
        GeoRegionCsvListToGeoRegionEntityListMapper(mapper = mapper)

    // RegionTlCsv
    @Singleton
    @Provides
    fun provideGeoRegionTlEntityToGeoRegionTlCsvMapper(): GeoRegionTlEntityToGeoRegionTlCsvMapper =
        GeoRegionTlEntityToGeoRegionTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoRegionTlEntityListToGeoRegionTlCsvListMapper(mapper: GeoRegionTlEntityToGeoRegionTlCsvMapper): GeoRegionTlEntityListToGeoRegionTlCsvListMapper =
        GeoRegionTlEntityListToGeoRegionTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionTlCsvToGeoRegionTlEntityMapper(): GeoRegionTlCsvToGeoRegionTlEntityMapper =
        GeoRegionTlCsvToGeoRegionTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionTlCsvListToGeoRegionTlEntityListMapper(mapper: GeoRegionTlCsvToGeoRegionTlEntityMapper): GeoRegionTlCsvListToGeoRegionTlEntityListMapper =
        GeoRegionTlCsvListToGeoRegionTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionCsvMappers(
        geoRegionEntityListToGeoRegionCsvListMapper: GeoRegionEntityListToGeoRegionCsvListMapper,
        geoRegionCsvListToGeoRegionEntityListMapper: GeoRegionCsvListToGeoRegionEntityListMapper,
        geoRegionTlEntityListToGeoRegionTlCsvListMapper: GeoRegionTlEntityListToGeoRegionTlCsvListMapper,
        geoRegionTlCsvListToGeoRegionTlEntityListMapper: GeoRegionTlCsvListToGeoRegionTlEntityListMapper
    ): GeoRegionCsvMappers = GeoRegionCsvMappers(
        geoRegionEntityListToGeoRegionCsvListMapper,
        geoRegionCsvListToGeoRegionEntityListMapper,
        geoRegionTlEntityListToGeoRegionTlCsvListMapper,
        geoRegionTlCsvListToGeoRegionTlEntityListMapper
    )

    // RegionDistrictCsv
    @Singleton
    @Provides
    fun provideGeoRegionDistrictEntityToGeoRegionDistrictCsvMapper(): GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper =
        GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper(mapper: GeoRegionDistrictEntityToGeoRegionDistrictCsvMapper): GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper =
        GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictCsvToGeoRegionDistrictEntityMapper(): GeoRegionDistrictCsvToGeoRegionDistrictEntityMapper =
        GeoRegionDistrictCsvToGeoRegionDistrictEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper(mapper: GeoRegionDistrictCsvToGeoRegionDistrictEntityMapper): GeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper =
        GeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper(mapper = mapper)

    // RegionDistrictTlCsv
    @Singleton
    @Provides
    fun provideGeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper(): GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper =
        GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper(mapper: GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper): GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper =
        GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper(): GeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper =
        GeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper(mapper: GeoRegionDistrictTlCsvToGeoRegionDistrictTlEntityMapper): GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper =
        GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictCsvMappers(
        geoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper: GeoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper,
        geoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper: GeoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper: GeoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper,
        geoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper: GeoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper
    ): GeoRegionDistrictCsvMappers = GeoRegionDistrictCsvMappers(
        geoRegionDistrictEntityListToGeoRegionDistrictCsvListMapper,
        geoRegionDistrictCsvListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictTlEntityListToGeoRegionDistrictTlCsvListMapper,
        geoRegionDistrictTlCsvListToGeoRegionDistrictTlEntityListMapper
    )

    // LocalityCsv
    @Singleton
    @Provides
    fun provideGeoLocalityEntityToGeoLocalityCsvMapper(): GeoLocalityEntityToGeoLocalityCsvMapper =
        GeoLocalityEntityToGeoLocalityCsvMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityEntityListToGeoLocalityCsvListMapper(mapper: GeoLocalityEntityToGeoLocalityCsvMapper): GeoLocalityEntityListToGeoLocalityCsvListMapper =
        GeoLocalityEntityListToGeoLocalityCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityCsvToGeoLocalityEntityMapper(): GeoLocalityCsvToGeoLocalityEntityMapper =
        GeoLocalityCsvToGeoLocalityEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityCsvListToGeoLocalityEntityListMapper(mapper: GeoLocalityCsvToGeoLocalityEntityMapper): GeoLocalityCsvListToGeoLocalityEntityListMapper =
        GeoLocalityCsvListToGeoLocalityEntityListMapper(mapper = mapper)

    // LocalityTlCsv
    @Singleton
    @Provides
    fun provideGeoLocalityTlEntityToGeoLocalityTlCsvMapper(): GeoLocalityTlEntityToGeoLocalityTlCsvMapper =
        GeoLocalityTlEntityToGeoLocalityTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityTlEntityListToGeoLocalityTlCsvListMapper(mapper: GeoLocalityTlEntityToGeoLocalityTlCsvMapper): GeoLocalityTlEntityListToGeoLocalityTlCsvListMapper =
        GeoLocalityTlEntityListToGeoLocalityTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityTlCsvToGeoLocalityTlEntityMapper(): GeoLocalityTlCsvToGeoLocalityTlEntityMapper =
        GeoLocalityTlCsvToGeoLocalityTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityTlCsvListToGeoLocalityTlEntityListMapper(mapper: GeoLocalityTlCsvToGeoLocalityTlEntityMapper): GeoLocalityTlCsvListToGeoLocalityTlEntityListMapper =
        GeoLocalityTlCsvListToGeoLocalityTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityCsvMappers(
        geoLocalityEntityListToGeoLocalityCsvListMapper: GeoLocalityEntityListToGeoLocalityCsvListMapper,
        geoLocalityCsvListToGeoLocalityEntityListMapper: GeoLocalityCsvListToGeoLocalityEntityListMapper,
        geoLocalityTlEntityListToGeoLocalityTlCsvListMapper: GeoLocalityTlEntityListToGeoLocalityTlCsvListMapper,
        geoLocalityTlCsvListToGeoLocalityTlEntityListMapper: GeoLocalityTlCsvListToGeoLocalityTlEntityListMapper
    ): GeoLocalityCsvMappers = GeoLocalityCsvMappers(
        geoLocalityEntityListToGeoLocalityCsvListMapper,
        geoLocalityCsvListToGeoLocalityEntityListMapper,
        geoLocalityTlEntityListToGeoLocalityTlCsvListMapper,
        geoLocalityTlCsvListToGeoLocalityTlEntityListMapper
    )

    // LocalityDistrictCsv
    @Singleton
    @Provides
    fun provideGeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper(): GeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper =
        GeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper(mapper: GeoLocalityDistrictEntityToGeoLocalityDistrictCsvMapper): GeoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper =
        GeoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper(): GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper =
        GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper(mapper: GeoLocalityDistrictCsvToGeoLocalityDistrictEntityMapper): GeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper =
        GeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper(mapper = mapper)

    // LocalityDistrictTlCsv
    @Singleton
    @Provides
    fun provideGeoLocalityDistrictTlEntityToGeoLocalityDistrictTlCsvMapper(): GeoLocalityDistrictTlEntityToGeoLocalityDistrictTlCsvMapper =
        GeoLocalityDistrictTlEntityToGeoLocalityDistrictTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper(mapper: GeoLocalityDistrictTlEntityToGeoLocalityDistrictTlCsvMapper): GeoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper =
        GeoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper(): GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper =
        GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper(mapper: GeoLocalityDistrictTlCsvToGeoLocalityDistrictTlEntityMapper): GeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper =
        GeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictCsvMappers(
        geoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper: GeoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper,
        geoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper: GeoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper: GeoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper,
        geoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper: GeoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper
    ): GeoLocalityDistrictCsvMappers = GeoLocalityDistrictCsvMappers(
        geoLocalityDistrictEntityListToGeoLocalityDistrictCsvListMapper,
        geoLocalityDistrictCsvListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictTlEntityListToGeoLocalityDistrictTlCsvListMapper,
        geoLocalityDistrictTlCsvListToGeoLocalityTlEntityListMapper
    )

    // MicrodistrictCsv
    @Singleton
    @Provides
    fun provideGeoMicrodistrictEntityToGeoMicrodistrictCsvMapper(): GeoMicrodistrictEntityToGeoMicrodistrictCsvMapper =
        GeoMicrodistrictEntityToGeoMicrodistrictCsvMapper()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper(mapper: GeoMicrodistrictEntityToGeoMicrodistrictCsvMapper): GeoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper =
        GeoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictCsvToGeoMicrodistrictEntityMapper(): GeoMicrodistrictCsvToGeoMicrodistrictEntityMapper =
        GeoMicrodistrictCsvToGeoMicrodistrictEntityMapper()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper(mapper: GeoMicrodistrictCsvToGeoMicrodistrictEntityMapper): GeoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper =
        GeoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper(mapper = mapper)

    // MicrodistrictTlCsv
    @Singleton
    @Provides
    fun provideGeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper(): GeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper =
        GeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper(mapper: GeoMicrodistrictTlEntityToGeoMicrodistrictTlCsvMapper): GeoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper =
        GeoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper(): GeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper =
        GeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper(mapper: GeoMicrodistrictTlCsvToGeoMicrodistrictTlEntityMapper): GeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper =
        GeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictCsvMappers(
        geoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper: GeoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper,
        geoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper: GeoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper: GeoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper,
        geoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper: GeoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper
    ): GeoMicrodistrictCsvMappers = GeoMicrodistrictCsvMappers(
        geoMicrodistrictEntityListToGeoMicrodistrictCsvListMapper,
        geoMicrodistrictCsvListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictTlEntityListToGeoMicrodistrictTlCsvListMapper,
        geoMicrodistrictTlCsvListToGeoMicrodistrictTlEntityListMapper
    )

    // StreetCsv
    @Singleton
    @Provides
    fun provideGeoStreetEntityToGeoStreetCsvMapper(): GeoStreetEntityToGeoStreetCsvMapper =
        GeoStreetEntityToGeoStreetCsvMapper()

    @Singleton
    @Provides
    fun provideGeoStreetEntityListToGeoStreetCsvListMapper(mapper: GeoStreetEntityToGeoStreetCsvMapper): GeoStreetEntityListToGeoStreetCsvListMapper =
        GeoStreetEntityListToGeoStreetCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetCsvToGeoStreetEntityMapper(): GeoStreetCsvToGeoStreetEntityMapper =
        GeoStreetCsvToGeoStreetEntityMapper()

    @Singleton
    @Provides
    fun provideGeoStreetCsvListToGeoStreetEntityListMapper(mapper: GeoStreetCsvToGeoStreetEntityMapper): GeoStreetCsvListToGeoStreetEntityListMapper =
        GeoStreetCsvListToGeoStreetEntityListMapper(mapper = mapper)

    // StreetTlCsv
    @Singleton
    @Provides
    fun provideGeoStreetTlEntityToGeoStreetTlCsvMapper(): GeoStreetTlEntityToGeoStreetTlCsvMapper =
        GeoStreetTlEntityToGeoStreetTlCsvMapper()

    @Singleton
    @Provides
    fun provideGeoStreetTlEntityListToGeoStreetTlCsvListMapper(mapper: GeoStreetTlEntityToGeoStreetTlCsvMapper): GeoStreetTlEntityListToGeoStreetTlCsvListMapper =
        GeoStreetTlEntityListToGeoStreetTlCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetTlCsvToGeoStreetTlEntityMapper(): GeoStreetTlCsvToGeoStreetTlEntityMapper =
        GeoStreetTlCsvToGeoStreetTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoStreetTlCsvListToGeoStreetTlEntityListMapper(mapper: GeoStreetTlCsvToGeoStreetTlEntityMapper): GeoStreetTlCsvListToGeoStreetTlEntityListMapper =
        GeoStreetTlCsvListToGeoStreetTlEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetCsvMappers(
        geoStreetEntityListToGeoStreetCsvListMapper: GeoStreetEntityListToGeoStreetCsvListMapper,
        geoStreetCsvListToGeoStreetEntityListMapper: GeoStreetCsvListToGeoStreetEntityListMapper,
        geoStreetTlEntityListToGeoStreetTlCsvListMapper: GeoStreetTlEntityListToGeoStreetTlCsvListMapper,
        geoStreetTlCsvListToGeoStreetTlEntityListMapper: GeoStreetTlCsvListToGeoStreetTlEntityListMapper
    ): GeoStreetCsvMappers = GeoStreetCsvMappers(
        geoStreetEntityListToGeoStreetCsvListMapper,
        geoStreetCsvListToGeoStreetEntityListMapper,
        geoStreetTlEntityListToGeoStreetTlCsvListMapper,
        geoStreetTlCsvListToGeoStreetTlEntityListMapper
    )

    // ------------------------------------------- API: -------------------------------------------
    @Singleton
    @Provides
    fun provideGeometryToGeoCoordinatesMapper(): GeometryToGeoCoordinatesMapper =
        GeometryToGeoCoordinatesMapper()

    // CountryElement
    @Singleton
    @Provides
    fun provideCountryElementToGeoCountryMapper(mapper: GeometryToGeoCoordinatesMapper): CountryElementToGeoCountryMapper =
        CountryElementToGeoCountryMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCountryElementsListToGeoCountriesListMapper(mapper: CountryElementToGeoCountryMapper): CountryElementsListToGeoCountriesListMapper =
        CountryElementsListToGeoCountriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoCountryApiMappers(
        countryElementsListToGeoCountriesListMapper: CountryElementsListToGeoCountriesListMapper
    ): GeoCountryApiMappers = GeoCountryApiMappers(countryElementsListToGeoCountriesListMapper)

    // RegionElement
    @Singleton
    @Provides
    fun provideRegionElementToGeoRegionMapper(@ApplicationContext ctx: Context, mapper: GeometryToGeoCoordinatesMapper): RegionElementToGeoRegionMapper =
        RegionElementToGeoRegionMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionElementsListToGeoRegionsListMapper(mapper: RegionElementToGeoRegionMapper): RegionElementsListToGeoRegionsListMapper =
        RegionElementsListToGeoRegionsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionApiMappers(
        regionElementsListToGeoRegionsListMapper: RegionElementsListToGeoRegionsListMapper
    ): GeoRegionApiMappers = GeoRegionApiMappers(regionElementsListToGeoRegionsListMapper)

    // RegionDistrictElement
    @Singleton
    @Provides
    fun provideRegionDistrictElementToGeoRegionDistrictMapper(mapper: GeometryToGeoCoordinatesMapper): RegionDistrictElementToGeoRegionDistrictMapper =
        RegionDistrictElementToGeoRegionDistrictMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictElementsListToGeoRegionDistrictsListMapper(mapper: RegionDistrictElementToGeoRegionDistrictMapper): RegionDistrictElementsListToGeoRegionDistrictsListMapper =
        RegionDistrictElementsListToGeoRegionDistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictApiMappers(
        regionDistrictElementsListToGeoRegionDistrictsListMapper: RegionDistrictElementsListToGeoRegionDistrictsListMapper
    ): GeoRegionDistrictApiMappers =
        GeoRegionDistrictApiMappers(regionDistrictElementsListToGeoRegionDistrictsListMapper)

    // LocalityElement
    @Singleton
    @Provides
    fun provideLocalityElementToGeoLocalityMapper(mapper: GeometryToGeoCoordinatesMapper): LocalityElementToGeoLocalityMapper =
        LocalityElementToGeoLocalityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityElementsListToGeoLocalitiesListMapper(mapper: LocalityElementToGeoLocalityMapper): LocalityElementsListToGeoLocalitiesListMapper =
        LocalityElementsListToGeoLocalitiesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityApiMappers(
        localityElementsListToGeoLocalitiesListMapper: LocalityElementsListToGeoLocalitiesListMapper
    ): GeoLocalityApiMappers =
        GeoLocalityApiMappers(localityElementsListToGeoLocalitiesListMapper)
}