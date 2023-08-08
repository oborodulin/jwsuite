package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityListToAppSettingListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityToAppSettingMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingMappers
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingToAppSettingEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationToCongregationEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewListToCongregationsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationsListToCongregationEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.FavoriteCongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceEntityListToEntrancesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceEntityToEntranceMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceMappers
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntranceToEntranceEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.entrance.EntrancesListToEntranceEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityListToFloorsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorEntityToFloorMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorMappers
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorToFloorEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.floor.FloorsListToFloorEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalitiesListToGeoLocalityEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityMappers
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityToGeoLocalityEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityToGeoLocalityTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewListToGeoLocalitiesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictToGeoLocalityDistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewListToGeoLocalityDistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictsListToGeoLocalityDistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewListToGeoMicrodistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionToGeoRegionEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionToGeoRegionTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewListToGeoRegionsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionsListToGeoRegionEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictMappers
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictToGeoRegionDistrictTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictViewListToGeoRegionDistrictsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictsListToGeoRegionDistrictEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetToGeoStreetEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetToGeoStreetTlEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewListToGeoStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetsListToGeoStreetEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupMappers
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupToGroupEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupViewListToGroupsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupsListToGroupEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseMappers
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseToHouseEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewListToHousesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseViewToHouseMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HousesListToHouseEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data.local.db.mappers.member.MemberToMemberEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.member.MemberViewListToMembersListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.member.MemberViewToMemberMapper
import com.oborodulin.jwsuite.data.local.db.mappers.member.MembersListToMemberEntityListMapper
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
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryCategoriesListToTerritoryCategoryEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryCategoryEntityListToTerritoryCategoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryCategoryEntityToTerritoryCategoryMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryCategoryMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryCategoryToTerritoryCategoryEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryLocationViewListToTerritoryLocationsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryLocationViewToTerritoryLocationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetHouseViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetMappers
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetToTerritoryStreetEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetViewListToTerritoryStreetsListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetViewToTerritoryStreetMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryStreetsListToTerritoryStreetEntityListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryToTerritoryEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryViewListToTerritoriesListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    // GEO:
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
    fun provideLocalityViewToGeoLocalityMapper(): LocalityViewToGeoLocalityMapper =
        LocalityViewToGeoLocalityMapper()

    @Singleton
    @Provides
    fun provideGeoLocalityViewToGeoLocalityMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper
    ): GeoLocalityViewToGeoLocalityMapper = GeoLocalityViewToGeoLocalityMapper(
        regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper
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
        localityMapper: LocalityViewToGeoLocalityMapper
    ): GeoLocalityDistrictViewToGeoLocalityDistrictMapper =
        GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
            regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper
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
    fun provideGeoMicrodistrictViewToGeoMicrodistrictMapper(
        regionMapper: GeoRegionViewToGeoRegionMapper,
        regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
        localityMapper: LocalityViewToGeoLocalityMapper,
        localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
    ): GeoMicrodistrictViewToGeoMicrodistrictMapper =
        GeoMicrodistrictViewToGeoMicrodistrictMapper(
            regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper,
            localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper
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
        localityMapper: GeoLocalityViewToGeoLocalityMapper,
        localityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
        microdistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper
    ): GeoStreetViewToGeoStreetMapper = GeoStreetViewToGeoStreetMapper(
        localityMapper = localityMapper, localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
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
        geoStreetToGeoStreetTlEntityMapper: GeoStreetToGeoStreetTlEntityMapper,
        territoryStreetViewListToTerritoryStreetsListMapper: TerritoryStreetViewListToTerritoryStreetsListMapper
    ): GeoStreetMappers = GeoStreetMappers(
        geoStreetViewListToGeoStreetsListMapper,
        geoStreetViewToGeoStreetMapper,
        geoStreetsListToGeoStreetEntityListMapper,
        geoStreetToGeoStreetEntityMapper,
        geoStreetToGeoStreetTlEntityMapper,
        territoryStreetViewListToTerritoryStreetsListMapper
    )

    // CONGREGATION:
    // Congregations:
    @Singleton
    @Provides
    fun provideCongregationViewToCongregationMapper(): CongregationViewToCongregationMapper =
        CongregationViewToCongregationMapper()

    @Singleton
    @Provides
    fun provideCongregationViewListToCongregationsListMapper(mapper: CongregationViewToCongregationMapper): CongregationViewListToCongregationsListMapper =
        CongregationViewListToCongregationsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideFavoriteCongregationViewToCongregationMapper(mapper: CongregationViewToCongregationMapper): FavoriteCongregationViewToCongregationMapper =
        FavoriteCongregationViewToCongregationMapper(congregationViewMapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationToCongregationEntityMapper(): CongregationToCongregationEntityMapper =
        CongregationToCongregationEntityMapper()

    @Singleton
    @Provides
    fun provideCongregationsListToCongregationEntityListMapper(mapper: CongregationToCongregationEntityMapper): CongregationsListToCongregationEntityListMapper =
        CongregationsListToCongregationEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideCongregationMappers(
        congregationViewListToCongregationsListMapper: CongregationViewListToCongregationsListMapper,
        congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
        congregationsListToCongregationEntityListMapper: CongregationsListToCongregationEntityListMapper,
        congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper,
        favoriteCongregationViewToCongregationMapper: FavoriteCongregationViewToCongregationMapper
    ): CongregationMappers = CongregationMappers(
        congregationViewListToCongregationsListMapper,
        congregationViewToCongregationMapper,
        congregationsListToCongregationEntityListMapper,
        congregationToCongregationEntityMapper,
        favoriteCongregationViewToCongregationMapper
    )

    // Groups:
    @Singleton
    @Provides
    fun provideGroupViewToGroupMapper(mapper: CongregationViewToCongregationMapper): GroupViewToGroupMapper =
        GroupViewToGroupMapper(congregationMapper = mapper)

    @Singleton
    @Provides
    fun provideGroupViewListToGroupsListMapper(mapper: GroupViewToGroupMapper): GroupViewListToGroupsListMapper =
        GroupViewListToGroupsListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupToGroupEntityMapper(): GroupToGroupEntityMapper = GroupToGroupEntityMapper()

    @Singleton
    @Provides
    fun provideGroupsListToGroupEntityListMapper(mapper: GroupToGroupEntityMapper): GroupsListToGroupEntityListMapper =
        GroupsListToGroupEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideGroupMappers(
        groupViewListToGroupsListMapper: GroupViewListToGroupsListMapper,
        groupViewToGroupMapper: GroupViewToGroupMapper,
        groupsListToGroupEntityListMapper: GroupsListToGroupEntityListMapper,
        groupToGroupEntityMapper: GroupToGroupEntityMapper
    ): GroupMappers = GroupMappers(
        groupViewListToGroupsListMapper,
        groupViewToGroupMapper,
        groupsListToGroupEntityListMapper,
        groupToGroupEntityMapper
    )

    // Members:
    @Singleton
    @Provides
    fun provideMemberViewToMemberMapper(mapper: GroupViewToGroupMapper): MemberViewToMemberMapper =
        MemberViewToMemberMapper(groupMapper = mapper)

    @Singleton
    @Provides
    fun provideMMemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper): MemberViewListToMembersListMapper =
        MemberViewListToMembersListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberToMemberEntityMapper(): MemberToMemberEntityMapper =
        MemberToMemberEntityMapper()

    @Singleton
    @Provides
    fun provideMembersListToMemberEntityListMapper(mapper: MemberToMemberEntityMapper): MembersListToMemberEntityListMapper =
        MembersListToMemberEntityListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideMemberMappers(
        memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
        memberViewToMemberMapper: MemberViewToMemberMapper,
        membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
        memberToMemberEntityMapper: MemberToMemberEntityMapper
    ): MemberMappers = MemberMappers(
        memberViewListToMembersListMapper,
        memberViewToMemberMapper,
        membersListToMemberEntityListMapper,
        memberToMemberEntityMapper
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
    fun provideHouseViewToHouseMapper(mapper: GeoStreetViewToGeoStreetMapper): HouseViewToHouseMapper =
        HouseViewToHouseMapper(streetMapper = mapper)

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
    fun provideEntranceEntityToEntranceMapper(): EntranceEntityToEntranceMapper =
        EntranceEntityToEntranceMapper()

    @Singleton
    @Provides
    fun provideEntranceEntityListToEntrancesListMapper(mapper: EntranceEntityToEntranceMapper): EntranceEntityListToEntrancesListMapper =
        EntranceEntityListToEntrancesListMapper(mapper = mapper)

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
        entranceEntityListToEntrancesListMapper: EntranceEntityListToEntrancesListMapper,
        entranceEntityToEntranceMapper: EntranceEntityToEntranceMapper,
        entrancesListToEntranceEntityListMapper: EntrancesListToEntranceEntityListMapper,
        entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
    ): EntranceMappers = EntranceMappers(
        entranceEntityListToEntrancesListMapper,
        entranceEntityToEntranceMapper,
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
        streetMapper: GeoStreetViewToGeoStreetMapper
    ): TerritoryStreetHouseViewListToTerritoryStreetsListMapper =
        TerritoryStreetHouseViewListToTerritoryStreetsListMapper(
            territoryStreetMapper = territoryStreetMapper, streetMapper = streetMapper
        )

    // Territories:
    @Singleton
    @Provides
    fun provideTerritoryViewToTerritoryMapper(
        congregationMapper: CongregationViewToCongregationMapper,
        territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
        localityMapper: GeoLocalityViewToGeoLocalityMapper
    ): TerritoryViewToTerritoryMapper = TerritoryViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper
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
        localityMapper: GeoLocalityViewToGeoLocalityMapper,
        memberMapper: MemberViewToMemberMapper
    ): TerritoriesAtWorkViewToTerritoryMapper = TerritoriesAtWorkViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper,
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
        localityMapper: GeoLocalityViewToGeoLocalityMapper,
        memberMapper: MemberViewToMemberMapper
    ): TerritoriesHandOutViewToTerritoryMapper = TerritoriesHandOutViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper,
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
        localityMapper: GeoLocalityViewToGeoLocalityMapper
    ): TerritoriesIdleViewToTerritoryMapper = TerritoriesIdleViewToTerritoryMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper
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
        entranceEntityListToEntrancesListMapper: EntranceEntityListToEntrancesListMapper,
        floorEntityListToFloorsListMapper: FloorEntityListToFloorsListMapper,
        roomEntityListToRoomsListMapper: RoomEntityListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper: TerritoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper: TerritoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper: TerritoriesIdleViewListToTerritoriesListMapper,
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
        entranceEntityListToEntrancesListMapper,
        floorEntityListToFloorsListMapper,
        roomEntityListToRoomsListMapper,
        territoriesAtWorkViewListToTerritoriesListMapper,
        territoriesHandOutViewListToTerritoriesListMapper,
        territoriesIdleViewListToTerritoriesListMapper,
        memberToMemberEntityMapper
    )

}