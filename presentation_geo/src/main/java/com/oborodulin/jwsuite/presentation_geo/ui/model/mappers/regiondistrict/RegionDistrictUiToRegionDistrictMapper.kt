package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.CoordinatesUiToGeoCoordinatesMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper

class RegionDistrictUiToRegionDistrictMapper(
    private val regionUiMapper: RegionUiToRegionMapper,
    private val coordinatesUiMapper: CoordinatesUiToGeoCoordinatesMapper
) : Mapper<RegionDistrictUi, GeoRegionDistrict>,
    NullableMapper<RegionDistrictUi, GeoRegionDistrict> {
    override fun map(input: RegionDistrictUi) = GeoRegionDistrict(
        region = regionUiMapper.map(input.region!!),
        districtShortName = input.districtShortName,
        districtGeocode = input.districtGeocode,
        districtOsmId = input.districtOsmId,
        coordinates = coordinatesUiMapper.map(input.coordinates),
        districtName = input.districtName
    ).also { it.id = input.id }

    override fun nullableMap(input: RegionDistrictUi?) = input?.let { map(it) }
}