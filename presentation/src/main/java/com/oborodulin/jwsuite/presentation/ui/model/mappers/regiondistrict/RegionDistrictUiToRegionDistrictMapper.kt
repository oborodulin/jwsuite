package com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionUiToRegionMapper

class RegionDistrictUiToRegionDistrictMapper(private val regionUiMapper: RegionUiToRegionMapper) :
    Mapper<RegionDistrictUi, GeoRegionDistrict>,
    NullableMapper<RegionDistrictUi, GeoRegionDistrict> {
    override fun map(input: RegionDistrictUi): GeoRegionDistrict {
        val region = GeoRegionDistrict(
            region = regionUiMapper.map(input.region),
            districtShortName = input.districtShortName,
            districtName = input.districtName
        )
        region.id = input.id
        return region
    }

    override fun nullableMap(input: RegionDistrictUi?) = input?.let { map(it) }
}