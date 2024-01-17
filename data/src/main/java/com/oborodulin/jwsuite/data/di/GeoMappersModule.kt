package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality.GeoLocalityToGeoLocalityTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality.GeoLocalityViewListToGeoLocalitiesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictViewListToGeoMicrodistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.GeoRegionCsvMappers
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.GeoRegionCsvToGeoRegionEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.tl.GeoRegionTlCsvToGeoRegionTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.GeoRegionEntityListToGeoRegionCsvListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.GeoRegionEntityToGeoRegionCsvMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegion.GeoRegionCsvListToGeoRegionEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictViewListToGeoRegionDistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetToGeoStreetEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetToGeoStreetTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.mappers.csv.geostreet.GeoStreetsListToGeoStreetEntityListMapper
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
    fun provideGeoRegionViewToGeoRegionMapper(): GeoRegionEntityToGeoRegionCsvMapper =
        GeoRegionEntityToGeoRegionCsvMapper()

    @Singleton
    @Provides
    fun provideGeoRegionViewListToGeoRegionsListMapper(mapper: GeoRegionEntityToGeoRegionCsvMapper): GeoRegionEntityListToGeoRegionCsvListMapper =
        GeoRegionEntityListToGeoRegionCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionToGeoRegionEntityMapper(): GeoRegionCsvToGeoRegionEntityMapper =
        GeoRegionCsvToGeoRegionEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionToGeoRegionTlEntityMapper(): GeoRegionTlCsvToGeoRegionTlEntityMapper =
        GeoRegionTlCsvToGeoRegionTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoRegionsListToGeoRegionEntityListMapper(mapper: GeoRegionCsvToGeoRegionEntityMapper): GeoRegionCsvListToGeoRegionEntityListMapper =
        GeoRegionCsvListToGeoRegionEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGeoRegionMappers(
        geoRegionEntityListToGeoRegionCsvListMapper: GeoRegionEntityListToGeoRegionCsvListMapper,
        geoRegionEntityToGeoRegionCsvMapper: GeoRegionEntityToGeoRegionCsvMapper,
        geoRegionCsvListToGeoRegionEntityListMapper: GeoRegionCsvListToGeoRegionEntityListMapper,
        geoRegionCsvToGeoRegionEntityMapper: GeoRegionCsvToGeoRegionEntityMapper,
        geoRegionTlCsvToGeoRegionTlEntityMapper: GeoRegionTlCsvToGeoRegionTlEntityMapper
    ): GeoRegionCsvMappers = GeoRegionCsvMappers(
        geoRegionEntityListToGeoRegionCsvListMapper,
        geoRegionEntityToGeoRegionCsvMapper,
        geoRegionCsvListToGeoRegionEntityListMapper,
        geoRegionCsvToGeoRegionEntityMapper,
        geoRegionTlCsvToGeoRegionTlEntityMapper
    )

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictViewToGeoRegionDistrictMapper(): RegionDistrictViewToGeoRegionDistrictMapper =
        RegionDistrictViewToGeoRegionDistrictMapper()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictViewToGeoRegionDistrictMapper(mapper: GeoRegionEntityToGeoRegionCsvMapper): GeoRegionDistrictViewToGeoRegionDistrictMapper =
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
        regionMapper: GeoRegionEntityToGeoRegionCsvMapper,
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
    fun provideGeoLocalityToGeoLocalityEntityMapper(): com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper =
        com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityToGeoLocalityTlEntityMapper(): GeoLocalityToGeoLocalityTlEntityMapper =
        GeoLocalityToGeoLocalityTlEntityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalitiesListToGeoLocalityEntityListMapper(mapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper): com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper =
        com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper(
            mapper = mapper
        )

    @Singleton
    @Provides
    fun provideGeoLocalityMappers(
        geoLocalityViewListToGeoLocalitiesListMapper: GeoLocalityViewListToGeoLocalitiesListMapper,
        geoLocalityViewToGeoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
        geoLocalitiesListToGeoLocalityEntityListMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper,
        geoLocalityToGeoLocalityEntityMapper: com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper,
        geoLocalityToGeoLocalityTlEntityMapper: GeoLocalityToGeoLocalityTlEntityMapper
    ): com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityMappers =
        com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality.GeoLocalityMappers(
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
        regionMapper: GeoRegionEntityToGeoRegionCsvMapper,
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
        regionMapper: GeoRegionEntityToGeoRegionCsvMapper,
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
}