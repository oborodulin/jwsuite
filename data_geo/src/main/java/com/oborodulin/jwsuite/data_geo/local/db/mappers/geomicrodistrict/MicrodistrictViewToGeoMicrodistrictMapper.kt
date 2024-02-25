package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict

class MicrodistrictViewToGeoMicrodistrictMapper(
    private val ctx: Context,
    private val mapper: CoordinatesToGeoCoordinatesMapper
) : Mapper<MicrodistrictView, GeoMicrodistrict>,
    NullableMapper<MicrodistrictView, GeoMicrodistrict> {
    override fun map(input: MicrodistrictView) = GeoMicrodistrict(
        ctx = ctx,
        microdistrictType = input.data.microdistrictType,
        microdistrictShortName = input.data.microdistrictShortName,
        microdistrictGeocode = input.data.microdistrictGeocode,
        microdistrictOsmId = input.data.microdistrictOsmId,
        coordinates = mapper.map(input.data.coordinates),
        microdistrictName = input.tl.microdistrictName
    ).also {
        it.id = input.data.microdistrictId
        it.tlId = input.tl.microdistrictTlId
    }

    override fun nullableMap(input: MicrodistrictView?) = input?.let { map(it) }
}