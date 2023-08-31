package com.oborodulin.jwsuite.data_territory.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.domain.model.House

class HouseViewToHouseMapper(
    private val streetMapper: GeoStreetViewToGeoStreetMapper,
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val houseMapper: HouseEntityToHouseMapper
) : Mapper<HouseView, House> {
    override fun map(input: HouseView): House {
        val ldRegion = regionMapper.nullableMap(input.hldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.hldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.hldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.hmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.hmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.hmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.hmLocalityDistrict, mLocality
        )
        return houseMapper.map(
            input.house,
            streetMapper.map(input.street),
            localityDistrictMapper.nullableMap(input.hLocalityDistrict, ldLocality),
            microdistrictMapper.nullableMap(input.hMicrodistrict, mLocality, mLocalityDistrict),
            territoryMapper.nullableMap(input.territory)
        )
    }
}