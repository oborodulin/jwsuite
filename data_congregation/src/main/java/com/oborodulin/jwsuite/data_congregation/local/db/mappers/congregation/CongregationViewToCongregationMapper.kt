package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.domain.model.congregation.Congregation

class CongregationViewToCongregationMapper(
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val congregationMapper: CongregationEntityToCongregationMapper
) : Mapper<CongregationView, Congregation>, NullableMapper<CongregationView, Congregation> {
    override fun map(input: CongregationView): Congregation {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val locality = localityMapper.map(input.locality, region, regionDistrict)
        return congregationMapper.map(input.congregation, locality)
    }

    override fun nullableMap(input: CongregationView?) = input?.let { map(it) }
}