package com.oborodulin.jwsuite.data.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data.local.db.views.EntranceView
import com.oborodulin.jwsuite.domain.model.Entrance

class EntranceViewToEntranceMapper(
    private val streetMapper: GeoStreetViewToGeoStreetMapper,
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val houseMapper: HouseEntityToHouseMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper
) : Mapper<EntranceView, Entrance> {
    override fun map(input: EntranceView): Entrance {
        val ldRegion = regionMapper.nullableMap(input.eldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.eldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.eldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.emRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.emDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.emLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.emLocalityDistrict, mLocality
        )
        val entrance = Entrance(
            house = houseMapper.map(
                input.house,
                streetMapper.map(input.street),
                localityDistrictMapper.nullableMap(input.eLocalityDistrict, ldLocality),
                microdistrictMapper.nullableMap(input.eMicrodistrict, mLocality, mLocalityDistrict),
                territoryMapper.nullableMap(input.territory)
            ),
            territory = territoryMapper.nullableMap(input.territory),
            entranceNum = input.entrance.entranceNum,
            isSecurity = input.entrance.isSecurityEntrance,
            isIntercom = input.entrance.isIntercomEntrance,
            isResidential = input.entrance.isResidentialEntrance,
            floorsQty = input.entrance.entranceFloorsQty,
            roomsByFloor = input.entrance.roomsByEntranceFloor,
            estimatedRooms = input.entrance.estEntranceRooms,
            territoryDesc = input.entrance.entranceDesc,
        )
        entrance.id = input.entrance.entranceId
        return entrance
    }
}