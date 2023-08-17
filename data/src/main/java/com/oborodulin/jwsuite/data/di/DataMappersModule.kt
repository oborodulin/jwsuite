package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityListToAppSettingListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityToAppSettingMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingMappers
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingToAppSettingEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceToEntranceEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceViewListToEntrancesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceViewToEntranceMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntrancesListToEntranceEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityListToFloorsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityToFloorMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorToFloorEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorsListToFloorEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseToHouseEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HousesListToHouseEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomEntityListToRoomsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomEntityToRoomMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomMappers
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomToRoomEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.room.RoomsListToRoomEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesAtWorkViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesAtWorkViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesHandOutViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesHandOutViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesIdleViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesIdleViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoriesListToTerritoryEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetHouseViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryToTerritoryEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoriesListToTerritoryCategoryEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoryEntityListToTerritoryCategoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.category.TerritoryCategoryToTerritoryCategoryEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.location.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.location.TerritoryLocationViewToTerritoryLocationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.street.TerritoryStreetsListToTerritoryStreetEntityListMapper
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataMappersModule {
    // MAPPERS:
    // AppSettings:
    @Singleton
    @Provides
    fun provideAppSettingEntityToAppSettingMapper(): AppSettingEntityToAppSettingMapper =
        AppSettingEntityToAppSettingMapper()

    @Singleton
    @Provides
    fun provideAppSettingToAppSettingEntityMapper(): AppSettingToAppSettingEntityMapper =
        AppSettingToAppSettingEntityMapper()

    @Singleton
    @Provides
    fun provideAppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper): AppSettingEntityListToAppSettingListMapper =
        AppSettingEntityListToAppSettingListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideAppSettingMappers(
        appSettingEntityListToAppSettingListMapper: AppSettingEntityListToAppSettingListMapper,
        appSettingEntityToAppSettingMapper: AppSettingEntityToAppSettingMapper,
        appSettingToAppSettingEntityMapper: AppSettingToAppSettingEntityMapper
    ): AppSettingMappers = AppSettingMappers(
        appSettingEntityListToAppSettingListMapper,
        appSettingEntityToAppSettingMapper,
        appSettingToAppSettingEntityMapper
    )

    // TERRITORY:
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
    fun provideHouseEntityToHouseMapper(): HouseEntityToHouseMapper = HouseEntityToHouseMapper()

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
    fun provideEntranceEntityToEntranceMapper(
        streetMapper: GeoStreetViewToGeoStreetMapper,
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
        houseMapper: HouseEntityToHouseMapper,
        territoryMapper: TerritoryViewToTerritoryMapper
    ): EntranceViewToEntranceMapper =
        EntranceViewToEntranceMapper(
            streetMapper = streetMapper,
            regionMapper = regionMapper,
            regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper,
            localityDistrictMapper = localityDistrictMapper,
            microdistrictMapper = microdistrictMapper,
            houseMapper = houseMapper,
            territoryMapper = territoryMapper
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
    fun provideFloorEntityToFloorMapper(): FloorEntityToFloorMapper = FloorEntityToFloorMapper()

    @Singleton
    @Provides
    fun provideFloorEntityListToFloorsListMapper(mapper: FloorEntityToFloorMapper): FloorEntityListToFloorsListMapper =
        FloorEntityListToFloorsListMapper(mapper = mapper)

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
        floorEntityListToFloorsListMapper: FloorEntityListToFloorsListMapper,
        floorEntityToFloorMapper: FloorEntityToFloorMapper,
        floorsListToFloorEntityListMapper: FloorsListToFloorEntityListMapper,
        floorToFloorEntityMapper: FloorToFloorEntityMapper
    ): FloorMappers = FloorMappers(
        floorEntityListToFloorsListMapper,
        floorEntityToFloorMapper,
        floorsListToFloorEntityListMapper,
        floorToFloorEntityMapper
    )

    // Rooms:
    @Singleton
    @Provides
    fun provideRoomEntityToRoomMapper(): RoomEntityToRoomMapper = RoomEntityToRoomMapper()

    @Singleton
    @Provides
    fun provideRoomEntityListToRoomsListMapper(mapper: RoomEntityToRoomMapper): RoomEntityListToRoomsListMapper =
        RoomEntityListToRoomsListMapper(mapper = mapper)

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
        roomEntityListToRoomsListMapper: RoomEntityListToRoomsListMapper,
        roomEntityToRoomMapper: RoomEntityToRoomMapper,
        roomsListToRoomEntityListMapper: RoomsListToRoomEntityListMapper,
        roomToRoomEntityMapper: RoomToRoomEntityMapper
    ): RoomMappers = RoomMappers(
        roomEntityListToRoomsListMapper,
        roomEntityToRoomMapper,
        roomsListToRoomEntityListMapper,
        roomToRoomEntityMapper
    )

    // TerritoryStreets:
    @Singleton
    @Provides
    fun provideTerritoryStreetViewToTerritoryStreetMapper(mapper: GeoStreetViewToGeoStreetMapper): TerritoryStreetViewToTerritoryStreetMapper =
        TerritoryStreetViewToTerritoryStreetMapper(streetMapper = mapper)

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
        territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetViewToTerritoryStreetMapper: TerritoryStreetViewToTerritoryStreetMapper,
        territoryStreetsListToTerritoryStreetEntityListMapper: TerritoryStreetsListToTerritoryStreetEntityListMapper,
        territoryStreetToTerritoryStreetEntityMapper: TerritoryStreetToTerritoryStreetEntityMapper
    ): TerritoryStreetMappers = TerritoryStreetMappers(
        territoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetViewToTerritoryStreetMapper,
        territoryStreetsListToTerritoryStreetEntityListMapper,
        territoryStreetToTerritoryStreetEntityMapper
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
        territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetHouseViewListToTerritoryStreetsListMapper: TerritoryStreetHouseViewListToTerritoryStreetsListMapper,
        geoStreetViewListToGeoStreetsListMapper: GeoStreetViewListToGeoStreetsListMapper,
        houseViewListToHousesListMapper: HouseViewListToHousesListMapper,
        entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
        floorEntityListToFloorsListMapper: FloorEntityListToFloorsListMapper,
        roomEntityListToRoomsListMapper: RoomEntityListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper: TerritoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper: TerritoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper,
        territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper: TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper,
        memberToMemberEntityMapper: MemberToMemberEntityMapper
    ): TerritoryMappers = TerritoryMappers(
        territoryViewListToTerritoriesListMapper,
        territoryViewToTerritoryMapper,
        territoriesListToTerritoryEntityListMapper,
        territoryToTerritoryEntityMapper,
        territoryLocationViewListToTerritoryLocationsListMapper,
        territoryLocationViewToTerritoryLocationMapper,
        territoryStreetViewListToTerritoryStreetsListMapper,
        territoryStreetHouseViewListToTerritoryStreetsListMapper,
        geoStreetViewListToGeoStreetsListMapper,
        houseViewListToHousesListMapper,
        entranceViewListToEntrancesListMapper,
        floorEntityListToFloorsListMapper,
        roomEntityListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper,
        territoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper,
        memberToMemberEntityMapper
    )

}