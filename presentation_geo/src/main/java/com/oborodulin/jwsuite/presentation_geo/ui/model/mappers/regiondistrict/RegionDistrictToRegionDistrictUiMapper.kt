package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionUiMapper

class RegionDistrictToRegionDistrictUiMapper(
    private val regionMapper: RegionToRegionUiMapper,
    private val coordinatesMapper: GeoCoordinatesToCoordinatesUiMapper
) : NullableMapper<GeoRegionDistrict, RegionDistrictUi>,
    Mapper<GeoRegionDistrict, RegionDistrictUi> {
    override fun map(input: GeoRegionDistrict) = RegionDistrictUi(
        region = regionMapper.map(input.region!!),
        districtShortName = input.districtShortName,
        districtGeocode = input.districtGeocode,
        districtOsmId = input.districtOsmId,
        coordinates = coordinatesMapper.map(input.coordinates),
        districtName = input.districtName
    ).also { it.id = input.id }

    override fun nullableMap(input: GeoRegionDistrict?) = input?.let { map(it) }
}