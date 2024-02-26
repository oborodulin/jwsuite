package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceEntityToEntranceMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.floor.FloorEntityToFloorMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import com.oborodulin.jwsuite.domain.model.territory.Room

class RoomViewToRoomMapper(
    private val streetMapper: StreetViewToGeoStreetMapper,
    //private val regionMapper: RegionViewToGeoRegionMapper,
    //private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    //private val geoLocalityMapper: LocalityViewToGeoLocalityMapper,
    private val houseMapper: HouseEntityToHouseMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val entranceMapper: EntranceEntityToEntranceMapper,
    private val floorMapper: FloorEntityToFloorMapper,
    private val roomMapper: RoomEntityToRoomMapper
) : Mapper<RoomView, Room>, NullableMapper<RoomView, Room> {
    override fun map(input: RoomView): Room {
        val locality = localityMapper.map(input.locality)
        val street = streetMapper.map(input.street).also { it.locality = locality }
        val localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict)
        val microdistrict = microdistrictMapper.nullableMap(input.microdistrict)
        val house = houseMapper.map(input.house, street, localityDistrict, microdistrict, null)
        val entrance = entranceMapper.nullableMap(input.entrance, house, null)
        val floor = floorMapper.nullableMap(input.floor, house, entrance, null)
        return roomMapper.map(
            input.room,
            locality, localityDistrict, microdistrict, street,
            house, entrance, floor, territoryMapper.nullableMap(input.territory)
        )
    }

    override fun nullableMap(input: RoomView?) = input?.let { map(it) }
}
/*
: Room {
        val street = streetMapper.map(input.street)
        val sLocality = geoLocalityMapper.map(input.street.locality)

        val ldRegion = regionMapper.nullableMap(input.rldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.rldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.rldLocality, ldRegion, ldRegionDistrict)
        val hLocalityDistrict =
            localityDistrictMapper.nullableMap(input.localityDistrict, ldLocality)

        val mRegion = regionMapper.nullableMap(input.rmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.rmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.rmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.rmLocalityDistrict, mLocality
        )
        val hMicrodistrict =
            microdistrictMapper.nullableMap(input.microdistrict, mLocality, mLocalityDistrict)

        val house = houseMapper.map(input.house, street, hLocalityDistrict, hMicrodistrict, null)
        val entrance = entranceEntityMapper.nullableMap(input.entrance, house, null)
        val floor = floorEntityMapper.nullableMap(input.floor, house, entrance, null)
        return roomEntityMapper.map(
            input.room,
            sLocality, hLocalityDistrict, hMicrodistrict, street,
            house, entrance, floor, territoryMapper.nullableMap(input.territory)
        )
    }
 */