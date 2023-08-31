package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper

class LocalityDistrictToLocalityDistrictUiMapper(private val mapper: LocalityToLocalityUiMapper) :
    NullableMapper<GeoLocalityDistrict, LocalityDistrictUi>,
    Mapper<GeoLocalityDistrict, LocalityDistrictUi> {
    override fun map(input: GeoLocalityDistrict): LocalityDistrictUi {
        val localityDistrictUi = LocalityDistrictUi(
            locality = mapper.map(input.locality),
            districtShortName = input.districtShortName,
            districtName = input.districtName
        )
        localityDistrictUi.id = input.id
        return localityDistrictUi
    }

    override fun nullableMap(input: GeoLocalityDistrict?) = input?.let { map(it) }
}