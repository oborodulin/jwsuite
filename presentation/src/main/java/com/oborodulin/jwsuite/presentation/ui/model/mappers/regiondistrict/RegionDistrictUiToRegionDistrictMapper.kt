package com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi

class RegionDistrictUiToRegionDistrictMapper : Mapper<RegionDistrictUi, GeoRegionDistrict> {
    override fun map(input: RegionDistrictUi): GeoRegionDistrict {
        val region = GeoRegionDistrict(
            regionId = input.regionId,
            districtShortName = input.districtShortName,
            districtName = input.districtName
        )
        region.id = input.id
        return region
    }
}