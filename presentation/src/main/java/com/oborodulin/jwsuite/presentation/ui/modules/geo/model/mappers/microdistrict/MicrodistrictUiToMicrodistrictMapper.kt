package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.microdistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper

class MicrodistrictUiToMicrodistrictMapper(
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper
) : Mapper<MicrodistrictUi, GeoMicrodistrict>,
    NullableMapper<MicrodistrictUi, GeoMicrodistrict> {
    override fun map(input: MicrodistrictUi): GeoMicrodistrict {
        val locality = GeoMicrodistrict(
            locality = localityUiMapper.map(input.locality),
            localityDistrict = localityDistrictUiMapper.map(input.localityDistrict),
            microdistrictType = input.microdistrictType,
            microdistrictShortName = input.microdistrictShortName,
            microdistrictName = input.microdistrictName
        )
        locality.id = input.id
        return locality
    }

    override fun nullableMap(input: MicrodistrictUi?) = input?.let { map(it) }
}