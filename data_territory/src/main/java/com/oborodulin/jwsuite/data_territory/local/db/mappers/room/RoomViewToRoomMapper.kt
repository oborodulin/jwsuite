package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceEntityToEntranceMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorEntityToFloorMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import com.oborodulin.jwsuite.domain.model.territory.Room

class RoomViewToRoomMapper(
    private val streetMapper: GeoStreetViewToGeoStreetMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: GeoLocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper,
    private val geoLocalityMapper: LocalityViewToGeoLocalityMapper,
    private val houseMapper: HouseEntityToHouseMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val entranceEntityMapper: EntranceEntityToEntranceMapper,
    private val floorEntityMapper: FloorEntityToFloorMapper,
    private val roomEntityMapper: RoomEntityToRoomMapper
) : Mapper<RoomView, Room>, NullableMapper<RoomView, Room> {
    override fun map(input: RoomView): Room {
        val street = streetMapper.map(input.street)
        val sLocality = geoLocalityMapper.map(input.street.locality)

        val ldRegion = regionMapper.nullableMap(input.rldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.rldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.rldLocality, ldRegion, ldRegionDistrict)
        val hLocalityDistrict =
            localityDistrictMapper.nullableMap(input.rLocalityDistrict, ldLocality)

        val mRegion = regionMapper.nullableMap(input.rmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.rmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.rmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.rmLocalityDistrict, mLocality
        )
        val hMicrodistrict =
            microdistrictMapper.nullableMap(input.rMicrodistrict, mLocality, mLocalityDistrict)

        val house = houseMapper.map(input.house, street, hLocalityDistrict, hMicrodistrict, null)
        val entrance = entranceEntityMapper.nullableMap(input.entrance, house, null)
        val floor = floorEntityMapper.nullableMap(input.floor, house, entrance, null)
        return roomEntityMapper.map(
            input.room,
            sLocality, hLocalityDistrict, hMicrodistrict, street,
            house, entrance, floor, territoryMapper.nullableMap(input.territory)
        )
    }

    override fun nullableMap(input: RoomView?) = input?.let { map(it) }
}