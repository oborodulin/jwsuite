package com.oborodulin.jwsuite.data_territory.di

import android.content.Context
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.FavoriteCongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberEntityToMemberMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceCsvListToEntranceEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceCsvToEntranceEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceEntityListToEntranceCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance.EntranceEntityToEntranceCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorCsvListToFloorEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorCsvToFloorEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorEntityListToFloorCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor.FloorEntityToFloorCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseCsvListToHouseEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseCsvToHouseEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseEntityListToHouseCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.house.HouseEntityToHouseCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomCsvListToRoomEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomCsvToRoomEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomEntityListToRoomCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.room.RoomEntityToRoomCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryCsvListToTerritoryEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryCsvToTerritoryEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryEntityListToTerritoryCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.TerritoryEntityToTerritoryCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation.CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation.CongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation.CongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation.CongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation.CongregationTerritoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member.TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member.TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member.TerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member.TerritoryMemberCrossRefEntityToTerritoryMemberCrossRefCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member.TerritoryMemberCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryCsvListToTerritoryCategoryEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryCsvToTerritoryCategoryEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryEntityListToTerritoryCategoryCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorycategory.TerritoryCategoryEntityToTerritoryCategoryCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryMemberReportCsvToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryMemberReportEntityListToTerritoryMemberReportCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryMemberReportEntityToTerritoryMemberReportCsvMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territoryreport.TerritoryReportCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetCsvListToTerritoryStreetEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetCsvMappers
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetCsvToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetEntityListToTerritoryStreetCsvListMapper
import com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet.TerritoryStreetEntityToTerritoryStreetCsvMapper
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
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryTotalViewToTerritoryTotalsMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.location.TerritoryLocationViewToTerritoryLocationMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoriesListToTerritoryCategoryEntityListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryEntityListToTerritoryCategoriesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorycategory.TerritoryCategoryToTerritoryCategoryEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportEntityToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportViewListToTerritoryMemberReportsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryMemberReportViewToTerritoryMemberReportMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.TerritoryReportMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.house.TerritoryReportHouseToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.house.TerritoryReportHouseViewListToTerritoryReportHousesListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.house.TerritoryReportHouseViewToTerritoryReportHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.room.TerritoryReportRoomToTerritoryMemberReportEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.room.TerritoryReportRoomViewListToTerritoryReportRoomsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.room.TerritoryReportRoomViewToTerritoryReportRoomMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.street.TerritoryReportStreetViewListToTerritoryReportStreetsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport.street.TerritoryReportStreetViewToTerritoryReportStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetMappers
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet.TerritoryStreetsListToTerritoryStreetEntityListMapper
import com.oborodulin.jwsuite.data_territory.remote.osm.mappers.house.HouseApiMappers
import com.oborodulin.jwsuite.data_territory.remote.osm.mappers.house.HouseElementToHouseMapper
import com.oborodulin.jwsuite.data_territory.remote.osm.mappers.house.HouseElementsListToHousesListMapper
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
    fun provideHouseEntityToHouseMapper(
        @ApplicationContext ctx: Context, mapper: CoordinatesToGeoCoordinatesMapper
    ): HouseEntityToHouseMapper = HouseEntityToHouseMapper(ctx = ctx, mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseViewToHouseMapper(
        streetMapper: StreetViewToGeoStreetMapper,
        //regionMapper: RegionViewToGeoRegionMapper,
        //regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        houseMapper: HouseEntityToHouseMapper
    ): HouseViewToHouseMapper = HouseViewToHouseMapper(
        streetMapper = streetMapper,
        //regionMapper = regionMapper,
        //regionDistrictMapper = regionDistrictMapper,
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
        streetMapper: StreetViewToGeoStreetMapper,
        //regionMapper: RegionViewToGeoRegionMapper,
        //regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceMapper: EntranceEntityToEntranceMapper
    ): EntranceViewToEntranceMapper = EntranceViewToEntranceMapper(
        streetMapper = streetMapper,
        //regionMapper = regionMapper,
        //regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceMapper = entranceMapper
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
        streetMapper: StreetViewToGeoStreetMapper,
        //regionMapper: RegionViewToGeoRegionMapper,
        //regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceMapper: EntranceEntityToEntranceMapper,
        floorMapper: FloorEntityToFloorMapper
    ): FloorViewToFloorMapper = FloorViewToFloorMapper(
        streetMapper = streetMapper,
        //regionMapper = regionMapper,
        //regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceMapper = entranceMapper,
        floorMapper = floorMapper
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
        streetMapper: StreetViewToGeoStreetMapper,
        //regionMapper: RegionViewToGeoRegionMapper,
        //regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        //geoLocalityMapper: LocalityViewToGeoLocalityMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper,
        entranceMapper: EntranceEntityToEntranceMapper,
        floorMapper: FloorEntityToFloorMapper,
        roomMapper: RoomEntityToRoomMapper
    ): RoomViewToRoomMapper = RoomViewToRoomMapper(
        streetMapper = streetMapper,
        //regionMapper = regionMapper,
        //regionDistrictMapper = regionDistrictMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper,
        //geoLocalityMapper = geoLocalityMapper,
        houseMapper = houseMapper,
        territoryMapper = territoryMapper,
        entranceMapper = entranceMapper,
        floorMapper = floorMapper,
        roomMapper = roomMapper
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
        @ApplicationContext ctx: Context, mapper: StreetViewToGeoStreetMapper
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
        streetViewListToGeoStreetsListMapper: StreetViewListToGeoStreetsListMapper,
        territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetViewToTerritoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        territoryStreetsListToTerritoryStreetEntityListMapper: TerritoryStreetsListToTerritoryStreetEntityListMapper,
        territoryStreetToTerritoryStreetEntityMapper: TerritoryStreetToTerritoryStreetEntityMapper,
        territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper: TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper,
    ): TerritoryStreetMappers = TerritoryStreetMappers(
        streetViewListToGeoStreetsListMapper,
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
        territoryReportMapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper
    ): TerritoryMemberReportViewToTerritoryMemberReportMapper =
        TerritoryMemberReportViewToTerritoryMemberReportMapper(
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
        //regionMapper: RegionViewToGeoRegionMapper,
        //regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper
    ): TerritoryViewToTerritoryMapper = TerritoryViewToTerritoryMapper(
        ctx = ctx,
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        //regionMapper = regionMapper,
        //regionDistrictMapper = regionDistrictMapper,
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
        territoryMapper: TerritoryViewToTerritoryMapper,
        memberMapper: MemberEntityToMemberMapper
    ): TerritoriesAtWorkViewToTerritoryMapper = TerritoriesAtWorkViewToTerritoryMapper(
        territoryMapper = territoryMapper, memberMapper = memberMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesAtWorkViewListToTerritoriesListMapper(mapper: TerritoriesAtWorkViewToTerritoryMapper): TerritoriesAtWorkViewListToTerritoriesListMapper =
        TerritoriesAtWorkViewListToTerritoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesHandOutViewToTerritoryMapper(
        territoryMapper: TerritoryViewToTerritoryMapper,
        memberMapper: MemberEntityToMemberMapper
    ): TerritoriesHandOutViewToTerritoryMapper = TerritoriesHandOutViewToTerritoryMapper(
        territoryMapper = territoryMapper, memberMapper = memberMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesHandOutViewListToTerritoriesListMapper(mapper: TerritoriesHandOutViewToTerritoryMapper): TerritoriesHandOutViewListToTerritoriesListMapper =
        TerritoriesHandOutViewListToTerritoriesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesIdleViewToTerritoryMapper(
        territoryMapper: TerritoryViewToTerritoryMapper
    ): TerritoriesIdleViewToTerritoryMapper = TerritoriesIdleViewToTerritoryMapper(
        territoryMapper = territoryMapper
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

    // TerritoryTotalView:
    @Singleton
    @Provides
    fun provideTerritoryTotalViewToTerritoryTotalsMapper(mapper: FavoriteCongregationViewToCongregationMapper): TerritoryTotalViewToTerritoryTotalsMapper =
        TerritoryTotalViewToTerritoryTotalsMapper(mapper = mapper)

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
        territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper,
        territoryTotalViewToTerritoryTotalsMapper: TerritoryTotalViewToTerritoryTotalsMapper,
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
        territoryTotalViewToTerritoryTotalsMapper,
        memberToMemberEntityMapper
    )

    // CSV:
    // HouseCsv:
    @Singleton
    @Provides
    fun provideHouseEntityToHouseCsvMapper(): HouseEntityToHouseCsvMapper =
        HouseEntityToHouseCsvMapper()

    @Singleton
    @Provides
    fun provideHouseEntityListToHouseCsvListMapper(mapper: HouseEntityToHouseCsvMapper): HouseEntityListToHouseCsvListMapper =
        HouseEntityListToHouseCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseCsvToHouseEntityMapper(): HouseCsvToHouseEntityMapper =
        HouseCsvToHouseEntityMapper()

    @Singleton
    @Provides
    fun provideHouseCsvListToHouseEntityListMapper(mapper: HouseCsvToHouseEntityMapper): HouseCsvListToHouseEntityListMapper =
        HouseCsvListToHouseEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseCsvMappers(
        houseEntityListToHouseCsvListMapper: HouseEntityListToHouseCsvListMapper,
        houseCsvListToHouseEntityListMapper: HouseCsvListToHouseEntityListMapper
    ): HouseCsvMappers = HouseCsvMappers(
        houseEntityListToHouseCsvListMapper,
        houseCsvListToHouseEntityListMapper
    )

    // EntranceCsv:
    @Singleton
    @Provides
    fun provideEntranceEntityToEntranceCsvMapper(): EntranceEntityToEntranceCsvMapper =
        EntranceEntityToEntranceCsvMapper()

    @Singleton
    @Provides
    fun provideEntranceEntityListToEntranceCsvListMapper(mapper: EntranceEntityToEntranceCsvMapper): EntranceEntityListToEntranceCsvListMapper =
        EntranceEntityListToEntranceCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEntranceCsvToEntranceEntityMapper(): EntranceCsvToEntranceEntityMapper =
        EntranceCsvToEntranceEntityMapper()

    @Singleton
    @Provides
    fun provideEntranceCsvListToEntranceEntityListMapper(mapper: EntranceCsvToEntranceEntityMapper): EntranceCsvListToEntranceEntityListMapper =
        EntranceCsvListToEntranceEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideEntranceCsvMappers(
        entranceEntityListToEntranceCsvListMapper: EntranceEntityListToEntranceCsvListMapper,
        entranceCsvListToEntranceEntityListMapper: EntranceCsvListToEntranceEntityListMapper
    ): EntranceCsvMappers = EntranceCsvMappers(
        entranceEntityListToEntranceCsvListMapper,
        entranceCsvListToEntranceEntityListMapper
    )

    // FloorCsv:
    @Singleton
    @Provides
    fun provideFloorEntityToFloorCsvMapper(): FloorEntityToFloorCsvMapper =
        FloorEntityToFloorCsvMapper()

    @Singleton
    @Provides
    fun provideFloorEntityListToFloorCsvListMapper(mapper: FloorEntityToFloorCsvMapper): FloorEntityListToFloorCsvListMapper =
        FloorEntityListToFloorCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFloorCsvToFloorEntityMapper(): FloorCsvToFloorEntityMapper =
        FloorCsvToFloorEntityMapper()

    @Singleton
    @Provides
    fun provideFloorCsvListToFloorEntityListMapper(mapper: FloorCsvToFloorEntityMapper): FloorCsvListToFloorEntityListMapper =
        FloorCsvListToFloorEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFloorCsvMappers(
        floorEntityListToFloorCsvListMapper: FloorEntityListToFloorCsvListMapper,
        floorCsvListToFloorEntityListMapper: FloorCsvListToFloorEntityListMapper
    ): FloorCsvMappers = FloorCsvMappers(
        floorEntityListToFloorCsvListMapper,
        floorCsvListToFloorEntityListMapper
    )

    // RoomCsv:
    @Singleton
    @Provides
    fun provideRoomEntityToRoomCsvMapper(): RoomEntityToRoomCsvMapper = RoomEntityToRoomCsvMapper()

    @Singleton
    @Provides
    fun provideRoomEntityListToRoomCsvListMapper(mapper: RoomEntityToRoomCsvMapper): RoomEntityListToRoomCsvListMapper =
        RoomEntityListToRoomCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomCsvToRoomEntityMapper(): RoomCsvToRoomEntityMapper = RoomCsvToRoomEntityMapper()

    @Singleton
    @Provides
    fun provideRoomCsvListToRoomEntityListMapper(mapper: RoomCsvToRoomEntityMapper): RoomCsvListToRoomEntityListMapper =
        RoomCsvListToRoomEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideRoomCsvMappers(
        roomEntityListToRoomCsvListMapper: RoomEntityListToRoomCsvListMapper,
        roomCsvListToRoomEntityListMapper: RoomCsvListToRoomEntityListMapper
    ): RoomCsvMappers = RoomCsvMappers(
        roomEntityListToRoomCsvListMapper,
        roomCsvListToRoomEntityListMapper
    )

    // TerritoryCategoryCsv:
    @Singleton
    @Provides
    fun provideTerritoryCategoryEntityToTerritoryCategoryCsvMapper(): TerritoryCategoryEntityToTerritoryCategoryCsvMapper =
        TerritoryCategoryEntityToTerritoryCategoryCsvMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoryEntityListToTerritoryCategoryCsvListMapper(mapper: TerritoryCategoryEntityToTerritoryCategoryCsvMapper): TerritoryCategoryEntityListToTerritoryCategoryCsvListMapper =
        TerritoryCategoryEntityListToTerritoryCategoryCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCategoryCsvToTerritoryCategoryEntityMapper(): TerritoryCategoryCsvToTerritoryCategoryEntityMapper =
        TerritoryCategoryCsvToTerritoryCategoryEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoryCsvListToTerritoryCategoryEntityListMapper(mapper: TerritoryCategoryCsvToTerritoryCategoryEntityMapper): TerritoryCategoryCsvListToTerritoryCategoryEntityListMapper =
        TerritoryCategoryCsvListToTerritoryCategoryEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCategoryCsvMappers(
        territoryCategoryEntityListToTerritoryCategoryCsvListMapper: TerritoryCategoryEntityListToTerritoryCategoryCsvListMapper,
        territoryCategoryCsvListToTerritoryCategoryEntityListMapper: TerritoryCategoryCsvListToTerritoryCategoryEntityListMapper
    ): TerritoryCategoryCsvMappers = TerritoryCategoryCsvMappers(
        territoryCategoryEntityListToTerritoryCategoryCsvListMapper,
        territoryCategoryCsvListToTerritoryCategoryEntityListMapper
    )

    // TerritoryMemberReportCsv:
    @Singleton
    @Provides
    fun provideTerritoryMemberReportEntityToTerritoryMemberReportCsvMapper(): TerritoryMemberReportEntityToTerritoryMemberReportCsvMapper =
        TerritoryMemberReportEntityToTerritoryMemberReportCsvMapper()

    @Singleton
    @Provides
    fun provideTerritoryMemberReportEntityListToTerritoryMemberReportCsvListMapper(mapper: TerritoryMemberReportEntityToTerritoryMemberReportCsvMapper): TerritoryMemberReportEntityListToTerritoryMemberReportCsvListMapper =
        TerritoryMemberReportEntityListToTerritoryMemberReportCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMemberReportCsvToTerritoryMemberReportEntityMapper(): TerritoryMemberReportCsvToTerritoryMemberReportEntityMapper =
        TerritoryMemberReportCsvToTerritoryMemberReportEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper(mapper: TerritoryMemberReportCsvToTerritoryMemberReportEntityMapper): TerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper =
        TerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryReportCsvMappers(
        territoryMemberReportEntityListToTerritoryMemberReportCsvListMapper: TerritoryMemberReportEntityListToTerritoryMemberReportCsvListMapper,
        territoryMemberReportCsvListToTerritoryMemberReportEntityListMapper: TerritoryMemberReportCsvListToTerritoryMemberReportEntityListMapper
    ): TerritoryReportCsvMappers = TerritoryReportCsvMappers(
        territoryMemberReportEntityListToTerritoryMemberReportCsvListMapper,
        territoryMemberReportCsvListToTerritoryMemberReportEntityListMapper
    )

    // TerritoryStreetCsv:
    @Singleton
    @Provides
    fun provideTerritoryStreetEntityToTerritoryStreetCsvMapper(): TerritoryStreetEntityToTerritoryStreetCsvMapper =
        TerritoryStreetEntityToTerritoryStreetCsvMapper()

    @Singleton
    @Provides
    fun provideTerritoryStreetEntityListToTerritoryStreetCsvListMapper(mapper: TerritoryStreetEntityToTerritoryStreetCsvMapper): TerritoryStreetEntityListToTerritoryStreetCsvListMapper =
        TerritoryStreetEntityListToTerritoryStreetCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetCsvToTerritoryStreetEntityMapper(): TerritoryStreetCsvToTerritoryStreetEntityMapper =
        TerritoryStreetCsvToTerritoryStreetEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryStreetCsvListToTerritoryStreetEntityListMapper(mapper: TerritoryStreetCsvToTerritoryStreetEntityMapper): TerritoryStreetCsvListToTerritoryStreetEntityListMapper =
        TerritoryStreetCsvListToTerritoryStreetEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetCsvMappers(
        territoryStreetEntityListToTerritoryStreetCsvListMapper: TerritoryStreetEntityListToTerritoryStreetCsvListMapper,
        territoryStreetCsvListToTerritoryStreetEntityListMapper: TerritoryStreetCsvListToTerritoryStreetEntityListMapper
    ): TerritoryStreetCsvMappers = TerritoryStreetCsvMappers(
        territoryStreetEntityListToTerritoryStreetCsvListMapper,
        territoryStreetCsvListToTerritoryStreetEntityListMapper
    )

    // CongregationTerritoryCrossRefCsv:
    @Singleton
    @Provides
    fun provideCongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper(): CongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper =
        CongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper()

    @Singleton
    @Provides
    fun provideCongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper(
        mapper: CongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper
    ): CongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper =
        CongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper(): CongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper =
        CongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper()

    @Singleton
    @Provides
    fun provideCongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper(
        mapper: CongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper
    ): CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper =
        CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationTerritoryCsvMappers(
        congregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper: CongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper,
        congregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper: CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper
    ): CongregationTerritoryCsvMappers = CongregationTerritoryCsvMappers(
        congregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper,
        congregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper
    )

    // TerritoryMemberCrossRefCsv:
    @Singleton
    @Provides
    fun provideTerritoryMemberCrossRefEntityToTerritoryMemberCrossRefCsvMapper(): TerritoryMemberCrossRefEntityToTerritoryMemberCrossRefCsvMapper =
        TerritoryMemberCrossRefEntityToTerritoryMemberCrossRefCsvMapper()

    @Singleton
    @Provides
    fun provideTerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper(
        mapper: TerritoryMemberCrossRefEntityToTerritoryMemberCrossRefCsvMapper
    ): TerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper =
        TerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper(): TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper =
        TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper(
        mapper: TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper
    ): TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper =
        TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryMemberCsvMappers(
        territoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper: TerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper,
        territoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper: TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper
    ): TerritoryMemberCsvMappers = TerritoryMemberCsvMappers(
        territoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper,
        territoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper
    )

    // TerritoryCsv:
    @Singleton
    @Provides
    fun provideTerritoryEntityToTerritoryCsvMapper(): TerritoryEntityToTerritoryCsvMapper =
        TerritoryEntityToTerritoryCsvMapper()

    @Singleton
    @Provides
    fun provideTerritoryEntityListToTerritoryCsvListMapper(mapper: TerritoryEntityToTerritoryCsvMapper): TerritoryEntityListToTerritoryCsvListMapper =
        TerritoryEntityListToTerritoryCsvListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCsvToTerritoryEntityMapper(): TerritoryCsvToTerritoryEntityMapper =
        TerritoryCsvToTerritoryEntityMapper()

    @Singleton
    @Provides
    fun provideTerritoryCsvListToTerritoryEntityListMapper(mapper: TerritoryCsvToTerritoryEntityMapper): TerritoryCsvListToTerritoryEntityListMapper =
        TerritoryCsvListToTerritoryEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCsvMappers(
        territoryEntityListToTerritoryCsvListMapper: TerritoryEntityListToTerritoryCsvListMapper,
        congregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper: CongregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper,
        territoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper: TerritoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper,
        territoryCsvListToTerritoryEntityListMapper: TerritoryCsvListToTerritoryEntityListMapper,
        congregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper: CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper,
        territoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper: TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper
    ): TerritoryCsvMappers = TerritoryCsvMappers(
        territoryEntityListToTerritoryCsvListMapper,
        congregationTerritoryCrossRefEntityListToCongregationTerritoryCrossRefCsvListMapper,
        territoryMemberCrossRefEntityListToTerritoryMemberCrossRefCsvListMapper,
        territoryCsvListToTerritoryEntityListMapper,
        congregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper,
        territoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper
    )

    // ------------------------------------------- API: -------------------------------------------
    // HouseElement
    @Singleton
    @Provides
    fun provideHouseElementToHouseMapper(mapper: GeometryToGeoCoordinatesMapper): HouseElementToHouseMapper =
        HouseElementToHouseMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseElementsListToHousesListMapper(mapper: HouseElementToHouseMapper): HouseElementsListToHousesListMapper =
        HouseElementsListToHousesListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideHouseApiMappers(
        houseElementsListToHousesListMapper: HouseElementsListToHousesListMapper
    ): HouseApiMappers = HouseApiMappers(houseElementsListToHousesListMapper)
}