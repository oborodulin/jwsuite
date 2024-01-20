package com.oborodulin.jwsuite.data_geo.di

import android.content.Context
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
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityToGeoLocalityTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewListToGeoLocalitiesListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewListToGeoMicrodistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionToGeoRegionEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionToGeoRegionTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewListToGeoRegionsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionsListToGeoRegionEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictViewListToGeoRegionDistrictsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetToGeoStreetEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetToGeoStreetTlEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetsListToGeoStreetEntityListMapper
import com.oborodulin.jwsuite.domain.usecases.*
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
    // Regions:
    @Singleton
    @Provides
    fun provideGeoRegionViewToGeoRegionMapper(): GeoRegionViewToGeoRegionMapper =
        GeoRegionViewToGeoRegionMapper()

    @Singleton
    @Provides
    fun provideGeoRegionViewListToGeoRegionsListMapper(mapper: GeoRegionViewToGeoRegionMapper): GeoRegionViewListToGeoRegionsListMapper =
        GeoRegionViewListToGeoRegionsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionToGeoRegionEntityMapper(): GeoRegionToGeoRegionEntityMapper =
        GeoRegionToGeoRegionEntityMapper()

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
        geoRegionViewListToGeoRegionsListMapper: GeoRegionViewListToGeoRegionsListMapper,
        geoRegionViewToGeoRegionMapper: GeoRegionViewToGeoRegionMapper,
        geoRegionsListToGeoRegionEntityListMapper: GeoRegionsListToGeoRegionEntityListMapper,
        geoRegionToGeoRegionEntityMapper: GeoRegionToGeoRegionEntityMapper,
        geoRegionToGeoRegionTlEntityMapper: GeoRegionToGeoRegionTlEntityMapper
    ): GeoRegionMappers = GeoRegionMappers(
        geoRegionViewListToGeoRegionsListMapper,
        geoRegionViewToGeoRegionMapper,
        geoRegionsListToGeoRegionEntityListMapper,
        geoRegionToGeoRegionEntityMapper,
        geoRegionToGeoRegionTlEntityMapper
    )

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictViewToGeoRegionDistrictMapper(): RegionDistrictViewToGeoRegionDistrictMapper =
        RegionDistrictViewToGeoRegionDistrictMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictViewToGeoRegionDistrictMapper(mapper: GeoRegionViewToGeoRegionMapper): GeoRegionDistrictViewToGeoRegionDistrictMapper =
        GeoRegionDistrictViewToGeoRegionDistrictMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictViewListToGeoRegionDistrictsListMapper(mapper: GeoRegionDistrictViewToGeoRegionDistrictMapper): GeoRegionDistrictViewListToGeoRegionDistrictsListMapper =
        GeoRegionDistrictViewListToGeoRegionDistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionDistrictToGeoRegionDistrictEntityMapper(): GeoRegionDistrictToGeoRegionDistrictEntityMapper =
        GeoRegionDistrictToGeoRegionDistrictEntityMapper()

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
        geoRegionDistrictViewListToGeoRegionDistrictsListMapper: GeoRegionDistrictViewListToGeoRegionDistrictsListMapper,
        geoRegionDistrictViewToGeoRegionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        geoRegionDistrictsListToGeoRegionDistrictEntityListMapper: GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictToGeoRegionDistrictEntityMapper: GeoRegionDistrictToGeoRegionDistrictEntityMapper,
        geoRegionDistrictToGeoRegionDistrictTlEntityMapper: GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
    ): GeoRegionDistrictMappers = GeoRegionDistrictMappers(
        geoRegionDistrictViewListToGeoRegionDistrictsListMapper,
        geoRegionDistrictViewToGeoRegionDistrictMapper,
        geoRegionDistrictsListToGeoRegionDistrictEntityListMapper,
        geoRegionDistrictToGeoRegionDistrictEntityMapper,
        geoRegionDistrictToGeoRegionDistrictTlEntityMapper
    )

    // Localities:
    @Singleton
    @Provides
    fun provideLocalityViewToGeoLocalityMapper(@ApplicationContext ctx: Context): LocalityViewToGeoLocalityMapper =
        LocalityViewToGeoLocalityMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideGeoLocalityViewToGeoLocalityMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper
    ): GeoLocalityViewToGeoLocalityMapper = GeoLocalityViewToGeoLocalityMapper(
        regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper
    )

    @Singleton
    @Provides
    fun provideGeoLocalityViewListToGeoLocalitiesListMapper(mapper: GeoLocalityViewToGeoLocalityMapper): GeoLocalityViewListToGeoLocalitiesListMapper =
        GeoLocalityViewListToGeoLocalitiesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityToGeoLocalityEntityMapper(): GeoLocalityToGeoLocalityEntityMapper =
        GeoLocalityToGeoLocalityEntityMapper()

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
        geoLocalityViewListToGeoLocalitiesListMapper: GeoLocalityViewListToGeoLocalitiesListMapper,
        geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
        geoLocalitiesListToGeoLocalityEntityListMapper: GeoLocalitiesListToGeoLocalityEntityListMapper,
        geoLocalityToGeoLocalityEntityMapper: GeoLocalityToGeoLocalityEntityMapper,
        geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
    ): GeoLocalityMappers = GeoLocalityMappers(
        geoLocalityViewListToGeoLocalitiesListMapper,
        geoLocalityViewToGeoLocalityMapper,
        geoLocalitiesListToGeoLocalityEntityListMapper,
        geoLocalityToGeoLocalityEntityMapper,
        geoLocalityToGeoLocalityTlEntityMapper
    )

    // LocalityDistricts:
    @Singleton
    @Provides
    fun provideLocalityDistrictViewToGeoLocalityDistrictMapper(): LocalityDistrictViewToGeoLocalityDistrictMapper =
        LocalityDistrictViewToGeoLocalityDistrictMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictViewToGeoLocalityDistrictMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
    ): GeoLocalityDistrictViewToGeoLocalityDistrictMapper =
        GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
            regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper
        )

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper): GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper =
        GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictToGeoLocalityDistrictEntityMapper(): GeoLocalityDistrictToGeoLocalityDistrictEntityMapper =
        GeoLocalityDistrictToGeoLocalityDistrictEntityMapper()

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
        geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper: GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper,
        geoLocalityDistrictViewToGeoLocalityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
        geoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper: GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictToGeoLocalityDistrictEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictEntityMapper,
        geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper: GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
    ): GeoLocalityDistrictMappers = GeoLocalityDistrictMappers(
        geoLocalityDistrictViewListToGeoLocalityDistrictsListMapper,
        geoLocalityDistrictViewToGeoLocalityDistrictMapper,
        geoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper,
        geoLocalityDistrictToGeoLocalityDistrictEntityMapper,
        geoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
    )

    // Microdistricts:
    @Singleton
    @Provides
    fun provideMicrodistrictViewToGeoMicrodistrictMapper(@ApplicationContext ctx: Context): MicrodistrictViewToGeoMicrodistrictMapper =
        MicrodistrictViewToGeoMicrodistrictMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictViewToGeoMicrodistrictMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
    ): GeoMicrodistrictViewToGeoMicrodistrictMapper =
        GeoMicrodistrictViewToGeoMicrodistrictMapper(
            regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper,
            microdistrictMapper = microdistrictMapper
        )

    @Singleton
    @Provides
    fun provideGeoMicrodistrictViewListToGeoMicrodistrictsListMapper(mapper: GeoMicrodistrictViewToGeoMicrodistrictMapper): GeoMicrodistrictViewListToGeoMicrodistrictsListMapper =
        GeoMicrodistrictViewListToGeoMicrodistrictsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoMicrodistrictToGeoMicrodistrictEntityMapper(): GeoMicrodistrictToGeoMicrodistrictEntityMapper =
        GeoMicrodistrictToGeoMicrodistrictEntityMapper()

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
        geoMicrodistrictViewListToGeoMicrodistrictsListMapper: GeoMicrodistrictViewListToGeoMicrodistrictsListMapper,
        geoMicrodistrictViewToGeoMicrodistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper,
        geoMicrodistrictsListToGeoMicrodistrictEntityListMapper: GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictToGeoMicrodistrictEntityMapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper,
        geoMicrodistrictToGeoMicrodistrictTlEntityMapper: GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
    ): GeoMicrodistrictMappers = GeoMicrodistrictMappers(
        geoMicrodistrictViewListToGeoMicrodistrictsListMapper,
        geoMicrodistrictViewToGeoMicrodistrictMapper,
        geoMicrodistrictsListToGeoMicrodistrictEntityListMapper,
        geoMicrodistrictToGeoMicrodistrictEntityMapper,
        geoMicrodistrictToGeoMicrodistrictTlEntityMapper
    )

    // Streets:
    @Singleton
    @Provides
    fun provideGeoStreetViewToGeoStreetMapper(
        @ApplicationContext ctx: Context,
        localityMapper: GeoLocalityViewToGeoLocalityMapper
    ): GeoStreetViewToGeoStreetMapper = GeoStreetViewToGeoStreetMapper(
        ctx = ctx, localityMapper = localityMapper
    )

    @Singleton
    @Provides
    fun provideGeoStreetViewListToGeoStreetsListMapper(mapper: GeoStreetViewToGeoStreetMapper): GeoStreetViewListToGeoStreetsListMapper =
        GeoStreetViewListToGeoStreetsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoStreetToGeoStreetEntityMapper(): GeoStreetToGeoStreetEntityMapper =
        GeoStreetToGeoStreetEntityMapper()

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
        geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
        geoStreetViewToGeoStreetMapper: GeoStreetViewToGeoStreetMapper,
        geoStreetsListToGeoStreetEntityListMapper: GeoStreetsListToGeoStreetEntityListMapper,
        geoStreetToGeoStreetEntityMapper: GeoStreetToGeoStreetEntityMapper,
        geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper
    ): GeoStreetMappers = GeoStreetMappers(
        geoStreetViewListToGeoStreetsListMapper,
        geoStreetViewToGeoStreetMapper,
        geoStreetsListToGeoStreetEntityListMapper,
        geoStreetToGeoStreetEntityMapper,
        geoStreetToGeoStreetTlEntityMapper
    )

    // CSV:
    // Region
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

    // RegionTl
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

    // RegionDistrict
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

    // RegionDistrictTl
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
}