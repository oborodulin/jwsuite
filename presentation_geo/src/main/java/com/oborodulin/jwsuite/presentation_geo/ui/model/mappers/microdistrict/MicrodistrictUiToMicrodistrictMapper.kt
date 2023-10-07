package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper

class MicrodistrictUiToMicrodistrictMapper(
    private val ctx: Context,
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper
) : Mapper<MicrodistrictUi, GeoMicrodistrict>, NullableMapper<MicrodistrictUi, GeoMicrodistrict> {
    override fun map(input: MicrodistrictUi): GeoMicrodistrict {
        val locality = GeoMicrodistrict(
            ctx = ctx,
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