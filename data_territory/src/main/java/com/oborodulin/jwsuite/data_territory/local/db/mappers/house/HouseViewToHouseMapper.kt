package com.oborodulin.jwsuite.data_territory.local.db.mappers.house

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.domain.model.territory.House

class HouseViewToHouseMapper(
    private val streetMapper: StreetViewToGeoStreetMapper,
    //private val regionMapper: RegionViewToGeoRegionMapper,
    //private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val houseMapper: HouseEntityToHouseMapper
) : Mapper<HouseView, House>, NullableMapper<HouseView, House> {
    override fun map(input: HouseView) = houseMapper.map(
        input.house,
        streetMapper.map(input.street)
            .also { it.locality = localityMapper.map(input.locality) },
        localityDistrictMapper.nullableMap(input.localityDistrict),
        microdistrictMapper.nullableMap(input.microdistrict),
        territoryMapper.nullableMap(input.territory)
    )

    override fun nullableMap(input: HouseView?) = input?.let { map(it) }
}
/*
: House {
        val ldRegion = regionMapper.nullableMap(input.hldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.hldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.hldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.hmRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.hmDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.hmLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.hmLocalityDistrict, mLocality
        )
        return
 = houseMapper.map(
        input.house,
        streetMapper.map(input.street)
            .also { it.locality = localityMapper.map(input.locality) },
        localityDistrictMapper.nullableMap(input.localityDistrict),// ldLocality),
        microdistrictMapper.nullableMap(input.microdistrict),// mLocality, mLocalityDistrict),
        territoryMapper.nullableMap(input.territory)
    )
 */