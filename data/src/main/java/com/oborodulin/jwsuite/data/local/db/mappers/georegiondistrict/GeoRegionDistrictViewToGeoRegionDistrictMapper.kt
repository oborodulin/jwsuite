package com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoRegionDistrictViewToGeoRegionDistrictMapper :
    Mapper<GeoRegionDistrictView, GeoRegionDistrict> {
    override fun map(input: GeoRegionDistrictView): GeoRegionDistrict {
        val regionDistrict = GeoRegionDistrict(
            regionId = input.data.regionsId,
            districtShortName = input.data.districtShortName,
            districtName = input.tl.districtName
        )
        regionDistrict.id = input.data.regionDistrictId
        regionDistrict.tlId = input.tl.regionDistrictTlId
        return regionDistrict
    }
}