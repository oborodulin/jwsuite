package com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict.LocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict.MicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.StreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.house.HouseEntityToHouseMapper
import com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.TerritoryViewToTerritoryMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import com.oborodulin.jwsuite.domain.model.territory.Entrance

class EntranceViewToEntranceMapper(
    private val streetMapper: StreetViewToGeoStreetMapper,
    //private val regionMapper: RegionViewToGeoRegionMapper,
    //private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: MicrodistrictViewToGeoMicrodistrictMapper,
    private val houseMapper: HouseEntityToHouseMapper,
    private val territoryMapper: TerritoryViewToTerritoryMapper,
    private val entranceMapper: EntranceEntityToEntranceMapper
) : Mapper<EntranceView, Entrance>, NullableMapper<EntranceView, Entrance> {
    override fun map(input: EntranceView) = entranceMapper.map(
        input.entrance,
        houseMapper.map(
            input.house,
            streetMapper.map(input.street)
                .also { it.locality = localityMapper.map(input.locality) },
            localityDistrictMapper.nullableMap(input.localityDistrict),
            microdistrictMapper.nullableMap(input.microdistrict),
            null
        ),
        territoryMapper.nullableMap(input.territory)
    )

    override fun nullableMap(input: EntranceView?) = input?.let { map(it) }
}
/*
: Entrance {
        val ldRegion = regionMapper.nullableMap(input.eldRegion)
        val ldRegionDistrict = regionDistrictMapper.nullableMap(input.eldDistrict, ldRegion)
        val ldLocality = localityMapper.nullableMap(input.eldLocality, ldRegion, ldRegionDistrict)

        val mRegion = regionMapper.nullableMap(input.emRegion)
        val mRegionDistrict = regionDistrictMapper.nullableMap(input.emDistrict, mRegion)
        val mLocality = localityMapper.nullableMap(input.emLocality, mRegion, mRegionDistrict)
        val mLocalityDistrict = localityDistrictMapper.nullableMap(
            input.emLocalityDistrict, mLocality
        )
        val house = houseMapper.map(
            input.house,
            streetMapper.map(input.street),
            localityDistrictMapper.nullableMap(input.localityDistrict, ldLocality),
            microdistrictMapper.nullableMap(input.microdistrict, mLocality, mLocalityDistrict),
            null
        )
        return entranceEntityMapper.map(
            input.entrance, house, territoryMapper.nullableMap(input.territory)
        )
    }
 */