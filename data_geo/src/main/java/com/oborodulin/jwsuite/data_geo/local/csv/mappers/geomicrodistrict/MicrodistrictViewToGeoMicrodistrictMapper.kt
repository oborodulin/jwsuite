package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geomicrodistrict

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView

class MicrodistrictViewToGeoMicrodistrictMapper(private val ctx: Context) :
    ConstructedMapper<MicrodistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv>,
    NullableConstructedMapper<MicrodistrictView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv> {
    override fun map(input: MicrodistrictView, vararg properties: Any?): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv {
        if (properties.size != 2 || properties[0] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv || properties[1] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv) throw IllegalArgumentException(
            "MicrodistrictViewToGeoMicrodistrictMapper: properties size not equal 2 or properties[0] is not GeoLocality class or properties[1] is not GeoLocalityDistrict class: size = %d; input.data.microdistrictId = %s".format(
                properties.size, input.data.microdistrictId
            )
        )
        val microdistrict =
            com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv(
                ctx = ctx,
                locality = properties[0] as com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv,
                localityDistrict = properties[1] as com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv,
                microdistrictType = input.data.microdistrictType,
                microdistrictShortName = input.data.microdistrictShortName,
                microdistrictName = input.tl.microdistrictName
            )
        microdistrict.id = input.data.microdistrictId
        microdistrict.tlId = input.tl.microdistrictTlId
        return microdistrict
    }

    override fun nullableMap(input: MicrodistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}