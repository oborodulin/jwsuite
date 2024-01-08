package com.oborodulin.jwsuite.data_territory.di

import android.content.Context
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceEntityToEntranceMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceToEntranceEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceViewListToEntrancesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceViewToEntranceMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntrancesListToEntranceEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorEntityToFloorMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorToFloorEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorViewListToFloorsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorViewToFloorMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorsListToFloorEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseToHouseEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HousesListToHouseEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomEntityToRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomToRoomEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewListToRoomsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomViewToRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.room.RoomsListToRoomEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesAtWorkViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesAtWorkViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesHandOutViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesHandOutViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesIdleViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesIdleViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoriesListToTerritoryEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryStreetHouseViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryToTerritoryEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoriesListToTerritoryCategoryEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryEntityListToTerritoryCategoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.category.TerritoryCategoryToTerritoryCategoryEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewToTerritoryLocationMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryMemberReportEntityToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryMemberReportToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryMemberReportViewListToTerritoryMemberReportsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryMemberReportViewToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportHouseToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportHouseViewListToTerritoryReportHousesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportHouseViewToTerritoryReportHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportRoomToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportRoomViewListToTerritoryReportRoomsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportRoomViewToTerritoryReportRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportStreetViewListToTerritoryReportStreetsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report.TerritoryReportStreetViewToTerritoryReportStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street.TerritoryStreetsListToTerritoryStreetEntityListMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TerritoryMappersModule {
    // TerritoryCategories:
    @Singleton
    @Provides
    fun provideTerritoryCategoryEntityToTerritoryCategoryMapper(): TerritoryCategoryEntityToTerritoryCategoryMapper =
        TerritoryCategoryEntityToTerritoryCategoryMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoryEntityListToTerritoryCategoriesListMapper(mapper: TerritoryCategoryEntityToTerritoryCategoryMapper): TerritoryCategoryEntityListToTerritoryCategoriesListMapper =
        TerritoryCategoryEntityListToTerritoryCategoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCategoryToTerritoryCategoryEntityMapper(): TerritoryCategoryToTerritoryCategoryEntityMapper =
        TerritoryCategoryToTerritoryCategoryEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoriesListToTerritoryCategoryEntityListMapper(mapper: TerritoryCategoryToTerritoryCategoryEntityMapper): TerritoryCategoriesListToTerritoryCategoryEntityListMapper =
        TerritoryCategoriesListToTerritoryCategoryEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCategoryMappers(
        territoryCategoryEntityListToTerritoryCategoriesListMapper: TerritoryCategoryEntityListToTerritoryCategoriesListMapper,
        territoryCategoryEntityToTerritoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        territoryCategoriesListToTerritoryCategoryEntityListMapper: TerritoryCategoriesListToTerritoryCategoryEntityListMapper,
        territoryCategoryToTerritoryCategoryEntityMapper: TerritoryCategoryToTerritoryCategoryEntityMapper
    ): TerritoryCategoryMappers = TerritoryCategoryMappers(
        territoryCategoryEntityListToTerritoryCategoriesListMapper,
        territoryCategoryEntityToTerritoryCategoryMapper,
        territoryCategoriesListToTerritoryCategoryEntityListMapper,
        territoryCategoryToTerritoryCategoryEntityMapper
    )

    // Houses:
    @Singleton
    @Provides
    fun provideHouseEntityToHouseMapper(@ApplicationContext ctx: Context): HouseEntityToHouseMapper =
        HouseEntityToHouseMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideHouseViewToHouseMapper(
        streetMapper: GeoStreetViewToGeoStreetMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        houseMapper: HouseEntityToHouseMapper
    ): HouseViewToHouseMapper =
        HouseViewToHouseMapper(
            streetMapper = streetMapper,
            regionMapper = regionMapper,
            regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper,
            localityDistrictMapper = localityDistrictMapper,
            microdistrictMapper = microdistrictMapper,
            territoryMapper = territoryMapper,
            houseMapper = houseMapper
        )

    @Singleton
    @Provides
    fun provideHouseViewListToHousesListMapper(mapper: HouseViewToHouseMapper): HouseViewListToHousesListMapper =
        HouseViewListToHousesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseToHouseEntityMapper(): HouseToHouseEntityMapper = HouseToHouseEntityMapper()

    @Singleton
    @Provides
    fun provideHousesListToHouseEntityListMapper(mapper: HouseToHouseEntityMapper): HousesListToHouseEntityListMapper =
        HousesListToHouseEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseMappers(
        houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
        houseViewToHouseMapper: HouseViewToHouseMapper,
        housesListToHouseEntityListMapper: HousesListToHouseEntityListMapper,
        houseToHouseEntityMapper: HouseToHouseEntityMapper
    ): HouseMappers = HouseMappers(
        houseViewListToHousesListMapper,
        houseViewToHouseMapper,
        housesListToHouseEntityListMapper,
        houseToHouseEntityMapper
    )

    // Entrances:
    @Singleton
    @Provides
    fun provideEntranceEntityToEntranceMapper(@ApplicationContext ctx: Context): EntranceEntityToEntranceMapper =
        EntranceEntityToEntranceMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideEntranceViewToEntranceMapper(
        streetMapper: GeoStreetViewToGeoStreetMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceEntityMapper: EntranceEntityToEntranceMapper
    ): EntranceViewToEntranceMapper = EntranceViewToEntranceMapper(
        streetMapper = streetMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceEntityMapper = entranceEntityMapper
    )

    @Singleton
    @Provides
    fun provideEntranceEntityListToEntrancesListMapper(mapper: EntranceViewToEntranceMapper): EntranceViewListToEntrancesListMapper =
        EntranceViewListToEntrancesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEntranceToEntranceEntityMapper(): EntranceToEntranceEntityMapper =
        EntranceToEntranceEntityMapper()

    @Singleton
    @Provides
    fun provideEntrancesListToEntranceEntityListMapper(mapper: EntranceToEntranceEntityMapper): EntrancesListToEntranceEntityListMapper =
        EntrancesListToEntranceEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEntranceMappers(
        entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
        entranceViewToEntranceMapper: EntranceViewToEntranceMapper,
        entrancesListToEntranceEntityListMapper: EntrancesListToEntranceEntityListMapper,
        entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
    ): EntranceMappers = EntranceMappers(
        entranceViewListToEntrancesListMapper,
        entranceViewToEntranceMapper,
        entrancesListToEntranceEntityListMapper,
        entranceToEntranceEntityMapper
    )

    // Floors:
    @Singleton
    @Provides
    fun provideFloorEntityToFloorMapper(@ApplicationContext ctx: Context): FloorEntityToFloorMapper =
        FloorEntityToFloorMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideFloorViewToFloorMapper(
        streetMapper: GeoStreetViewToGeoStreetMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceEntityMapper: EntranceEntityToEntranceMapper,
        floorEntityMapper: FloorEntityToFloorMapper
    ): FloorViewToFloorMapper = FloorViewToFloorMapper(
        streetMapper = streetMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceEntityMapper = entranceEntityMapper,
        floorEntityMapper = floorEntityMapper
    )

    @Singleton
    @Provides
    fun provideFloorViewListToFloorsListMapper(mapper: FloorViewToFloorMapper): FloorViewListToFloorsListMapper =
        FloorViewListToFloorsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFloorToFloorEntityMapper(): FloorToFloorEntityMapper = FloorToFloorEntityMapper()

    @Singleton
    @Provides
    fun provideFloorsListToFloorEntityListMapper(mapper: FloorToFloorEntityMapper): FloorsListToFloorEntityListMapper =
        FloorsListToFloorEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFloorMappers(
        floorViewListToFloorsListMapper: FloorViewListToFloorsListMapper,
        floorViewToFloorMapper: FloorViewToFloorMapper,
        floorEntityToFloorMapper: FloorEntityToFloorMapper,
        floorsListToFloorEntityListMapper: FloorsListToFloorEntityListMapper,
        floorToFloorEntityMapper: FloorToFloorEntityMapper
    ): FloorMappers = FloorMappers(
        floorViewListToFloorsListMapper,
        floorViewToFloorMapper,
        floorEntityToFloorMapper,
        floorsListToFloorEntityListMapper,
        floorToFloorEntityMapper
    )

    // Rooms:
    @Singleton
    @Provides
    fun provideRoomEntityToRoomMapper(@ApplicationContext ctx: Context): RoomEntityToRoomMapper =
        RoomEntityToRoomMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideRoomViewToRoomMapper(
        streetMapper: GeoStreetViewToGeoStreetMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        geoLocalityMapper: GeoLocalityViewToGeoLocalityMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceEntityMapper: EntranceEntityToEntranceMapper,
        floorEntityMapper: FloorEntityToFloorMapper,
        roomEntityMapper: RoomEntityToRoomMapper
    ): RoomViewToRoomMapper = RoomViewToRoomMapper(
        streetMapper = streetMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        geoLocalityMapper = geoLocalityMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceEntityMapper = entranceEntityMapper,
        floorEntityMapper = floorEntityMapper,
        roomEntityMapper = roomEntityMapper
    )

    @Singleton
    @Provides
    fun provideRoomViewListToRoomsListMapper(mapper: RoomViewToRoomMapper): RoomViewListToRoomsListMapper =
        RoomViewListToRoomsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomToRoomEntityMapper(): RoomToRoomEntityMapper = RoomToRoomEntityMapper()

    @Singleton
    @Provides
    fun provideRoomsListToRoomEntityListMapper(mapper: RoomToRoomEntityMapper): RoomsListToRoomEntityListMapper =
        RoomsListToRoomEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomMappers(
        roomViewListToRoomsListMapper: RoomViewListToRoomsListMapper,
        roomViewToRoomMapper: RoomViewToRoomMapper,
        roomEntityToRoomMapper: RoomEntityToRoomMapper,
        roomsListToRoomEntityListMapper: RoomsListToRoomEntityListMapper,
        roomToRoomEntityMapper: RoomToRoomEntityMapper
    ): RoomMappers = RoomMappers(
        roomViewListToRoomsListMapper,
        roomViewToRoomMapper,
        roomEntityToRoomMapper,
        roomsListToRoomEntityListMapper,
        roomToRoomEntityMapper
    )

    // TerritoryStreets:
    @Singleton
    @Provides
    fun provideTerritoryStreetViewToTerritoryStreetMapper(
        @ApplicationContext ctx: Context, mapper: GeoStreetViewToGeoStreetMapper
    ): TerritoryStreetViewToTerritoryStreetMapper =
        TerritoryStreetViewToTerritoryStreetMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetViewListToTerritoryStreetsListMapper(mapper: TerritoryStreetViewToTerritoryStreetMapper): TerritoryStreetViewListToTerritoryStreetsListMapper =
        TerritoryStreetViewListToTerritoryStreetsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetToTerritoryStreetEntityMapper(): TerritoryStreetToTerritoryStreetEntityMapper =
        TerritoryStreetToTerritoryStreetEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryStreetsListToTerritoryStreetEntityListMapper(mapper: TerritoryStreetToTerritoryStreetEntityMapper): TerritoryStreetsListToTerritoryStreetEntityListMapper =
        TerritoryStreetsListToTerritoryStreetEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetMappers(
        geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
        territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetViewToTerritoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        territoryStreetsListToTerritoryStreetEntityListMapper: TerritoryStreetsListToTerritoryStreetEntityListMapper,
        territoryStreetToTerritoryStreetEntityMapper: TerritoryStreetToTerritoryStreetEntityMapper,
        territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper: TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper,
    ): TerritoryStreetMappers = TerritoryStreetMappers(
        geoStreetViewListToGeoStreetsListMapper,
        territoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetViewToTerritoryStreetMapper,
        territoryStreetsListToTerritoryStreetEntityListMapper,
        territoryStreetToTerritoryStreetEntityMapper,
        territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper
    )

    // TerritoryStreetHouses:
    @Singleton
    @Provides
    fun provideTerritoryStreetHouseViewListToTerritoryStreetsListMapper(
        territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        territoryMapper: TerritoryViewToTerritoryMapper
    ): TerritoryStreetHouseViewListToTerritoryStreetsListMapper =
        TerritoryStreetHouseViewListToTerritoryStreetsListMapper(
            territoryStreetMapper = territoryStreetMapper, territoryMapper = territoryMapper
        )

    //Territory Reports:
    @Singleton
    @Provides
    fun provideTerritoryMemberReportEntityToTerritoryMemberReportMapper(
        @ApplicationContext ctx: Context
    ): TerritoryMemberReportEntityToTerritoryMemberReportMapper =
        TerritoryMemberReportEntityToTerritoryMemberReportMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideTerritoryMemberReportEntityListToTerritoryMemberReportsListMapper(
        mapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper =
        TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMemberReportViewToTerritoryMemberReportMapper(
        territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        houseMapper: HouseViewToHouseMapper,
        roomMapper: RoomViewToRoomMapper,
        territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryMemberReportViewToTerritoryMemberReportMapper =
        TerritoryMemberReportViewToTerritoryMemberReportMapper(
            territoryStreetMapper = territoryStreetMapper,
            houseMapper = houseMapper,
            roomMapper = roomMapper,
            territoryReportMapper = territoryReportMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryMemberReportToTerritoryMemberReportEntityMapper(): TerritoryMemberReportToTerritoryMemberReportEntityMapper =
        TerritoryMemberReportToTerritoryMemberReportEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryMemberReportViewListToTerritoryMemberReportsListMapper(
        mapper: TerritoryMemberReportViewToTerritoryMemberReportMapper
    ): TerritoryMemberReportViewListToTerritoryMemberReportsListMapper =
        TerritoryMemberReportViewListToTerritoryMemberReportsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportStreetViewToTerritoryReportStreetMapper(
        territoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryReportStreetViewToTerritoryReportStreetMapper =
        TerritoryReportStreetViewToTerritoryReportStreetMapper(
            territoryStreetMapper = territoryStreetMapper,
            territoryReportMapper = territoryReportMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryReportStreetViewListToTerritoryReportStreetsListMapper(
        mapper: TerritoryReportStreetViewToTerritoryReportStreetMapper
    ): TerritoryReportStreetViewListToTerritoryReportStreetsListMapper =
        TerritoryReportStreetViewListToTerritoryReportStreetsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportHouseViewToTerritoryReportHouseMapper(
        houseMapper: HouseViewToHouseMapper,
        territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryReportHouseViewToTerritoryReportHouseMapper =
        TerritoryReportHouseViewToTerritoryReportHouseMapper(
            houseMapper = houseMapper, territoryReportMapper = territoryReportMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryReportHouseViewListToTerritoryReportHousesListMapper(
        mapper: TerritoryReportHouseViewToTerritoryReportHouseMapper
    ): TerritoryReportHouseViewListToTerritoryReportHousesListMapper =
        TerritoryReportHouseViewListToTerritoryReportHousesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportRoomViewToTerritoryReportRoomMapper(
        roomMapper: RoomViewToRoomMapper,
        territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryReportRoomViewToTerritoryReportRoomMapper =
        TerritoryReportRoomViewToTerritoryReportRoomMapper(
            roomMapper = roomMapper, territoryReportMapper = territoryReportMapper
        )

    @Singleton
    @Provides
    fun provideTerritoryReportRoomViewListToTerritoryReportRoomsListMapper(
        mapper: TerritoryReportRoomViewToTerritoryReportRoomMapper
    ): TerritoryReportRoomViewListToTerritoryReportRoomsListMapper =
        TerritoryReportRoomViewListToTerritoryReportRoomsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportHouseToTerritoryMemberReportEntityMapper(mapper: TerritoryMemberReportToTerritoryMemberReportEntityMapper): TerritoryReportHouseToTerritoryMemberReportEntityMapper =
        TerritoryReportHouseToTerritoryMemberReportEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportRoomToTerritoryMemberReportEntityMapper(mapper: TerritoryMemberReportToTerritoryMemberReportEntityMapper): TerritoryReportRoomToTerritoryMemberReportEntityMapper =
        TerritoryReportRoomToTerritoryMemberReportEntityMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportMappers(
        territoryMemberReportViewListToTerritoryMemberReportsListMapper: TerritoryMemberReportViewListToTerritoryMemberReportsListMapper,
        territoryReportStreetViewToTerritoryReportStreetMapper: TerritoryReportStreetViewToTerritoryReportStreetMapper,
        territoryReportStreetViewListToTerritoryReportStreetsListMapper: TerritoryReportStreetViewListToTerritoryReportStreetsListMapper,
        territoryReportHouseViewToTerritoryReportHouseMapper: TerritoryReportHouseViewToTerritoryReportHouseMapper,
        territoryReportHouseViewListToTerritoryReportHousesListMapper: TerritoryReportHouseViewListToTerritoryReportHousesListMapper,
        territoryReportRoomViewToTerritoryReportRoomMapper: TerritoryReportRoomViewToTerritoryReportRoomMapper,
        territoryReportRoomViewListToTerritoryReportRoomsListMapper: TerritoryReportRoomViewListToTerritoryReportRoomsListMapper,
        territoryMemberReportEntityToTerritoryMemberReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper,
        territoryMemberReportEntityListToTerritoryMemberReportsListMapper: TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper,
        territoryMemberReportToTerritoryMemberReportEntityMapper: TerritoryMemberReportToTerritoryMemberReportEntityMapper,
        territoryReportHouseToTerritoryMemberReportEntityMapper: TerritoryReportHouseToTerritoryMemberReportEntityMapper,
        territoryReportRoomToTerritoryMemberReportEntityMapper: TerritoryReportRoomToTerritoryMemberReportEntityMapper
    ): TerritoryReportMappers = TerritoryReportMappers(
        territoryMemberReportViewListToTerritoryMemberReportsListMapper,
        territoryReportStreetViewToTerritoryReportStreetMapper,
        territoryReportStreetViewListToTerritoryReportStreetsListMapper,
        territoryReportHouseViewToTerritoryReportHouseMapper,
        territoryReportHouseViewListToTerritoryReportHousesListMapper,
        territoryReportRoomViewToTerritoryReportRoomMapper,
        territoryReportRoomViewListToTerritoryReportRoomsListMapper,
        territoryMemberReportEntityToTerritoryMemberReportMapper,
        territoryMemberReportEntityListToTerritoryMemberReportsListMapper,
        territoryMemberReportToTerritoryMemberReportEntityMapper,
        territoryReportHouseToTerritoryMemberReportEntityMapper,
        territoryReportRoomToTerritoryMemberReportEntityMapper
    )

    // Territories:
    @Singleton
    @Provides
    fun provideTerritoryViewToTerritoryMapper(
        @ApplicationContext ctx: Context,
        congregationMapper: CongregationViewToCongregationMapper,
        territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
    ): TerritoryViewToTerritoryMapper = TerritoryViewToTerritoryMapper(
        ctx = ctx,
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
    )

    @Singleton
    @Provides
    fun provideTerritoryViewListToTerritoriesListMapper(mapper: TerritoryViewToTerritoryMapper): TerritoryViewListToTerritoriesListMapper =
        TerritoryViewListToTerritoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryToTerritoryEntityMapper(): TerritoryToTerritoryEntityMapper =
        TerritoryToTerritoryEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoriesListToTerritoryEntityListMapper(mapper: TerritoryToTerritoryEntityMapper): TerritoriesListToTerritoryEntityListMapper =
        TerritoriesListToTerritoryEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesAtWorkViewToTerritoryMapper(
        congregationMapper: CongregationViewToCongregationMapper,
        territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        memberMapper: MemberViewToMemberMapper
    ): TerritoriesAtWorkViewToTerritoryMapper = TerritoriesAtWorkViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        memberMapper = memberMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesAtWorkViewListToTerritoriesListMapper(mapper: TerritoriesAtWorkViewToTerritoryMapper): TerritoriesAtWorkViewListToTerritoriesListMapper =
        TerritoriesAtWorkViewListToTerritoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesHandOutViewToTerritoryMapper(
        congregationMapper: CongregationViewToCongregationMapper,
        territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        memberMapper: MemberViewToMemberMapper
    ): TerritoriesHandOutViewToTerritoryMapper = TerritoriesHandOutViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        memberMapper = memberMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesHandOutViewListToTerritoriesListMapper(mapper: TerritoriesHandOutViewToTerritoryMapper): TerritoriesHandOutViewListToTerritoriesListMapper =
        TerritoriesHandOutViewListToTerritoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesIdleViewToTerritoryMapper(
        congregationMapper: CongregationViewToCongregationMapper,
        territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
    ): TerritoriesIdleViewToTerritoryMapper = TerritoriesIdleViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        regionMapper = regionMapper,
        regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesIdleViewListToTerritoriesListMapper(mapper: TerritoriesIdleViewToTerritoryMapper): TerritoriesIdleViewListToTerritoriesListMapper =
        TerritoriesIdleViewListToTerritoriesListMapper(mapper = mapper)

    // TerritoryLocationView:
    @Singleton
    @Provides
    fun provideTerritoryLocationViewToTerritoryLocationMapper(): TerritoryLocationViewToTerritoryLocationMapper =
        TerritoryLocationViewToTerritoryLocationMapper()

    @Singleton
    @Provides
    fun provideTerritoryLocationViewListToTerritoryLocationsListMapper(mapper: TerritoryLocationViewToTerritoryLocationMapper): TerritoryLocationViewListToTerritoryLocationsListMapper =
        TerritoryLocationViewListToTerritoryLocationsListMapper(mapper = mapper)

    // TerritoryStreetNamesAndHouseNumsView:
    @Singleton
    @Provides
    fun provideTerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper(): TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper =
        TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper()

    @Singleton
    @Provides
    fun provideTerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper(
        mapper: TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper
    ): TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper =
        TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMappers(
        territoryViewListToTerritoriesListMapper: TerritoryViewListToTerritoriesListMapper,
        territoryViewToTerritoryMapper: TerritoryViewToTerritoryMapper,
        territoriesListToTerritoryEntityListMapper: TerritoriesListToTerritoryEntityListMapper,
        territoryToTerritoryEntityMapper: TerritoryToTerritoryEntityMapper,
        territoryLocationViewListToTerritoryLocationsListMapper: TerritoryLocationViewListToTerritoryLocationsListMapper,
        territoryLocationViewToTerritoryLocationMapper: TerritoryLocationViewToTerritoryLocationMapper,
        territoryStreetHouseViewListToTerritoryStreetsListMapper: TerritoryStreetHouseViewListToTerritoryStreetsListMapper,
        houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
        entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
        floorViewListToFloorsListMapper: FloorViewListToFloorsListMapper,
        roomViewListToRoomsListMapper: RoomViewListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper: TerritoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper: TerritoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper
        memberToMemberEntityMapper: MemberToMemberEntityMapper
    ): TerritoryMappers = TerritoryMappers(
        territoryViewListToTerritoriesListMapper,
        territoryViewToTerritoryMapper,
        territoriesListToTerritoryEntityListMapper,
        territoryToTerritoryEntityMapper,
        territoryLocationViewListToTerritoryLocationsListMapper,
        territoryLocationViewToTerritoryLocationMapper,
        territoryStreetHouseViewListToTerritoryStreetsListMapper,
        houseViewListToHousesListMapper,
        entranceViewListToEntrancesListMapper,
        floorViewListToFloorsListMapper,
        roomViewListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper,
        memberToMemberEntityMapper
    )
}