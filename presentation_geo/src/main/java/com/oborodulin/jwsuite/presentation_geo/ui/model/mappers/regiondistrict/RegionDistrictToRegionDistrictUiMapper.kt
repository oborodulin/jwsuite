package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionUiMapper

class RegionDistrictToRegionDistrictUiMapper(private val regionMapper: RegionToRegionUiMapper) :
    NullableMapper<GeoRegionDistrict, RegionDistrictUi>,
    Mapper<GeoRegionDistrict, RegionDistrictUi> {
    override fun map(input: GeoRegionDistrict): RegionDistrictUi {
        val regionDistrictUi = RegionDistrictUi(
            region = regionMapper.map(input.region),
            districtShortName = input.districtShortName,
            districtName = input.districtName
        )
        regionDistrictUi.id = input.id
        return regionDistrictUi
    }

    override fun nullableMap(input: GeoRegionDistrict?) = input?.let { map(it) }
}