package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.data.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class RegionDistrictViewToGeoRegionDistrictMapper : Mapper<RegionDistrictView, GeoRegionDistrict>,
    NullableMapper<RegionDistrictView, GeoRegionDistrict> {
    override fun map(input: RegionDistrictView): GeoRegionDistrict {
        val regionDistrict = GeoRegionDistrict(
            districtShortName = input.data.regDistrictShortName,
            districtName = input.tl.regDistrictName
        )
        regionDistrict.id = input.district.regionDistrictId
        regionDistrict.tlId = input.tl.regionDistrictTlId
        return regionDistrict
    }

    override fun map(input: GeoRegionDistrictView?) = input?.let { map(it) }
}