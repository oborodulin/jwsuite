package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper
) : Mapper<GeoLocalityDistrictView, GeoLocalityDistrict>,
    NullableMapper<GeoLocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: GeoLocalityDistrictView): GeoLocalityDistrict {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val localityDistrict = GeoLocalityDistrict(
            locality = localityMapper.map(input.locality, region, regionDistrict),
            districtShortName = input.localityDistrict.data.locDistrictShortName,
            districtName = input.localityDistrict.tl.locDistrictName
        )
        localityDistrict.id = input.localityDistrict.data.localityDistrictId
        localityDistrict.tlId = input.localityDistrict.tl.localityDistrictTlId
        return localityDistrict
    }

    override fun nullableMap(input: GeoLocalityDistrictView?) = input?.let { map(it) }
}