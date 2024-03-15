package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper

class LocalityDistrictToLocalityDistrictUiMapper(private val mapper: LocalityToLocalityUiMapper) :
    Mapper<GeoLocalityDistrict, LocalityDistrictUi>,
    NullableMapper<GeoLocalityDistrict, LocalityDistrictUi> {
    override fun map(input: GeoLocalityDistrict) = LocalityDistrictUi(
        locality = mapper.nullableMap(input.locality),
        districtShortName = input.districtShortName,
        districtName = input.districtName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: GeoLocalityDistrict?) = input?.let { map(it) }
}