package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: LocalityDistrictViewToGeoLocalityDistrictMapper
) : Mapper<GeoLocalityDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv>,
    NullableMapper<GeoLocalityDistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv> {
    override fun map(input: GeoLocalityDistrictView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        return localityDistrictMapper.map(
            input.localityDistrict,
            localityMapper.map(input.locality, region, regionDistrict)
        )
    }

    override fun nullableMap(input: GeoLocalityDistrictView?) = input?.let { map(it) }
}