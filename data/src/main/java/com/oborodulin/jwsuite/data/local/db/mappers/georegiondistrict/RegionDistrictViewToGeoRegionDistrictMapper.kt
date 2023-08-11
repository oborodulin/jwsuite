package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class RegionDistrictViewToGeoRegionDistrictMapper :
    ConstructedMapper<RegionDistrictView, GeoRegionDistrict>,
    NullableConstructedMapper<RegionDistrictView, GeoRegionDistrict> {
    override fun map(input: RegionDistrictView, vararg properties: Any?): GeoRegionDistrict {
        if (properties.isEmpty() || properties[0] !is GeoRegion) throw IllegalArgumentException(
            "RegionDistrictViewToGeoRegionDistrictMapper properties empty or properties[0] is not GeoRegion class: input.id = %s".format(
                input.data.regionDistrictId
            )
        )
        val regionDistrict = GeoRegionDistrict(
            region = properties[0] as GeoRegion,
            districtShortName = input.data.regDistrictShortName,
            districtName = input.tl.regDistrictName
        )
        regionDistrict.id = input.data.regionDistrictId
        regionDistrict.tlId = input.tl.regionDistrictTlId
        return regionDistrict
    }

    override fun nullableMap(input: RegionDistrictView?, vararg properties: Any?) =
        input?.let { map(it, properties) }
}