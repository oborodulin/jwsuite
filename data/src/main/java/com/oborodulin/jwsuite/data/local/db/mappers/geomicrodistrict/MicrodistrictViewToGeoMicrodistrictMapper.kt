package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class MicrodistrictViewToGeoMicrodistrictMapper :
    ConstructedMapper<MicrodistrictView, GeoMicrodistrict>,
    NullableConstructedMapper<MicrodistrictView, GeoMicrodistrict> {
    override fun map(input: MicrodistrictView, vararg properties: Any?): GeoMicrodistrict {
        if (properties.size != 2 || properties[0] !is GeoLocality || properties[1] !is GeoLocalityDistrict) throw IllegalArgumentException(
            "Constructed Mapper properties size not equal 2 or properties[0] is not GeoLocality class or properties[1] is not GeoLocalityDistrict class: size = %d".format(
                properties.size
            )
        )
        val microdistrict = GeoMicrodistrict(
            locality = properties[0] as GeoLocality,
            localityDistrict = properties[1] as GeoLocalityDistrict,
            microdistrictType = input.data.microdistrictType,
            microdistrictShortName = input.data.microdistrictShortName,
            microdistrictName = input.tl.microdistrictName
        )
        microdistrict.id = input.data.microdistrictId
        microdistrict.tlId = input.tl.microdistrictTlId
        return microdistrict
    }

    override fun nullableMap(input: MicrodistrictView?, vararg properties: Any?) =
        input?.let { map(it, properties) }
}