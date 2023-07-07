package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoRegionDistrictViewToGeoRegionDistrictMapper(private val regionMapper: GeoRegionViewToGeoRegionMapper) :
    Mapper<GeoRegionDistrictView, GeoRegionDistrict>,
    NullableMapper<GeoRegionDistrictView, GeoRegionDistrict> {
    override fun map(input: GeoRegionDistrictView): GeoRegionDistrict {
        val regionDistrict = GeoRegionDistrict(
            region = regionMapper.map(input.region),
            districtShortName = input.district.data.regDistrictShortName,
            districtName = input.district.tl.regDistrictName
        )
        regionDistrict.id = input.district.data.regionDistrictId
        regionDistrict.tlId = input.district.tl.regionDistrictTlId
        return regionDistrict
    }

    override fun nullableMap(input: GeoRegionDistrictView?) = input?.let { map(it) }
}