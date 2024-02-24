package com.oborodulin.jwsuite.data_territory.local.db.mappers.floor

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance.EntranceEntityToEntranceMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import com.oborodulin.jwsuite.domain.model.territory.Floor

class FloorViewToFloorMapper(
    private val streetMapper: GeoStreetViewToGeoStreetMapper,
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val houseMapper: HouseEntityToHouseMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val entranceEntityMapper: EntranceEntityToEntranceMapper,
    private val floorEntityMapper: FloorEntityToFloorMapper
) : Mapper<FloorView, Floor>, NullableMapper<FloorView, Floor> {
    override fun map(input: FloorView): Floor {
        val ldRegion = regionMapper.nullableMap(input.fldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.fldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.fldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.fmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.fmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.fmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.fmLocalityDistrict, mLocality
        )
        val house = houseMapper.map(
            input.house,
            streetMapper.map(input.street),
            localityDistrictMapper.nullableMap(input.fLocalityDistrict, ldLocality),
            microdistrictMapper.nullableMap(input.fMicrodistrict, mLocality, mLocalityDistrict),
            null
        )
        val entrance = entranceEntityMapper.nullableMap(input.entrance, house, null)
        return floorEntityMapper.map(
            input.floor, house, entrance, territoryMapper.nullableMap(input.territory)
        )
    }

    override fun nullableMap(input: FloorView?) = input?.let { map(it) }
}