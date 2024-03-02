package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper

class StreetToStreetUiMapper(
    private val localityMapper: LocalityToLocalityUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
    private val microdistrictMapper: MicrodistrictToMicrodistrictUiMapper
) : Mapper<GeoStreet, StreetUi> {
    override fun map(input: GeoStreet) = StreetUi(
        locality = localityMapper.map(input.locality!!),
        //localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
        //microdistrict = microdistrictMapper.nullableMap(input.microdistrict),
        roadType = input.roadType,
        isPrivateSector = input.isPrivateSector,
        estimatedHouses = input.estimatedHouses,
        streetName = input.streetName,
        streetFullName = input.streetFullName
    ).also { it.id = input.id }
}