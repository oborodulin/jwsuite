package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.RegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
    private val regionMapper: RegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
) : Mapper<GeoLocalityDistrictView, GeoLocalityDistrict>,
    NullableMapper<GeoLocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: GeoLocalityDistrictView): GeoLocalityDistrict {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        return localityDistrictMapper.map(
            input.localityDistrict,
            localityMapper.map(input.locality, region, regionDistrict)
        )
    }

    override fun nullableMap(input: GeoLocalityDistrictView?) = input?.let { map(it) }
}