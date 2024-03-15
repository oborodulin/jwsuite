package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper

class LocalityDistrictUiToLocalityDistrictMapper(private val mapper: LocalityUiToLocalityMapper) :
    Mapper<LocalityDistrictUi, GeoLocalityDistrict>,
    NullableMapper<LocalityDistrictUi, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictUi) = GeoLocalityDistrict(
        locality = mapper.map(input.locality!!),
        districtShortName = input.districtShortName,
        districtName = input.districtName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: LocalityDistrictUi?) = input?.let { map(it) }
}