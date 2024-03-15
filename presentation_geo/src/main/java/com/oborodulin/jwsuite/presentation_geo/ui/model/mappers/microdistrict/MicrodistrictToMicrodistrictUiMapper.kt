package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper

class MicrodistrictToMicrodistrictUiMapper(
    private val localityMapper: LocalityToLocalityUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper
) : Mapper<GeoMicrodistrict, MicrodistrictUi>,
    NullableMapper<GeoMicrodistrict, MicrodistrictUi> {
    override fun map(input: GeoMicrodistrict) = MicrodistrictUi(
        locality = localityMapper.nullableMap(input.locality),
        localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
        microdistrictType = input.microdistrictType,
        microdistrictShortName = input.microdistrictShortName,
        microdistrictName = input.microdistrictName,
        microdistrictFullName = input.microdistrictFullName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: GeoMicrodistrict?) = input?.let { map(it) }
}