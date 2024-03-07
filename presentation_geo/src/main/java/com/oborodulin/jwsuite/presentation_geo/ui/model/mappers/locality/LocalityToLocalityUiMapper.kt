package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictUiMapper

class LocalityToLocalityUiMapper(
    private val regionMapper: RegionToRegionUiMapper,
    private val regionDistrictMapper: RegionDistrictToRegionDistrictUiMapper
) : Mapper<GeoLocality, LocalityUi>, NullableMapper<GeoLocality, LocalityUi> {
    override fun map(input: GeoLocality) = LocalityUi(
        region = regionMapper.nullableMap(input.region),
        regionDistrict = regionDistrictMapper.nullableMap(input.regionDistrict),
        localityCode = input.localityCode,
        localityType = input.localityType,
        localityShortName = input.localityShortName,
        localityName = input.localityName,
        localityFullName = input.localityFullName
    ).also { it.id = input.id }

    override fun nullableMap(input: GeoLocality?) = input?.let { map(it) }
}