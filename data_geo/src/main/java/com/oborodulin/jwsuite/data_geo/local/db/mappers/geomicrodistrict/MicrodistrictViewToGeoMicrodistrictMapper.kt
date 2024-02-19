package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict

class MicrodistrictViewToGeoMicrodistrictMapper(private val ctx: Context) :
    ConstructedMapper<MicrodistrictView, GeoMicrodistrict>,
    NullableConstructedMapper<MicrodistrictView, GeoMicrodistrict> {
    override fun map(input: MicrodistrictView, vararg properties: Any?): GeoMicrodistrict {
        if (properties.size != 2 || properties[0] !is GeoLocality || properties[1] !is GeoLocalityDistrict) throw IllegalArgumentException(
            "MicrodistrictViewToGeoMicrodistrictMapper: properties size not equal 2 or properties[0] is not GeoLocality class or properties[1] is not GeoLocalityDistrict class: size = %d; input.data.microdistrictId = %s".format(
                properties.size, input.data.microdistrictId
            )
        )
        return GeoMicrodistrict(
            ctx = ctx,
            locality = properties[0] as GeoLocality,
            localityDistrict = properties[1] as GeoLocalityDistrict,
            microdistrictType = input.data.microdistrictType,
            microdistrictShortName = input.data.microdistrictShortName,
            microdistrictName = input.tl.microdistrictName
        ).also {
            it.id = input.data.microdistrictId
            it.tlId = input.tl.microdistrictTlId
        }
    }

    override fun nullableMap(input: MicrodistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}