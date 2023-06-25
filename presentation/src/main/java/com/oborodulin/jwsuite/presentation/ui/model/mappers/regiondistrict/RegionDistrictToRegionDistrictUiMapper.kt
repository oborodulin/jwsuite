package com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi

class RegionDistrictToRegionDistrictUiMapper : Mapper<GeoRegionDistrict, RegionDistrictUi> {
    override fun map(input: GeoRegionDistrict): RegionDistrictUi {
        val regionDistrictUi = RegionDistrictUi(
            regionId = input.regionId,
            districtShortName = input.districtShortName,
            districtName = input.districtName
        )
        regionDistrictUi.id = input.id
        return regionDistrictUi
    }
}