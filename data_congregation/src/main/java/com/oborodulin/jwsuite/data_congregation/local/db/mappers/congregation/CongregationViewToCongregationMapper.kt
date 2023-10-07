package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.domain.model.congregation.Congregation

class CongregationViewToCongregationMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper
) : Mapper<CongregationView, Congregation>, NullableMapper<CongregationView, Congregation> {
    override fun map(input: CongregationView): Congregation {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val congregation = Congregation(
            congregationNum = input.congregation.congregationNum,
            congregationName = input.congregation.congregationName,
            territoryMark = input.congregation.territoryMark,
            isFavorite = input.congregation.isFavorite,
            locality = localityMapper.map(input.locality, region, regionDistrict),
        )
        congregation.id = input.congregation.congregationId
        return congregation
    }

    override fun nullableMap(input: CongregationView?) = input?.let { map(it) }
}