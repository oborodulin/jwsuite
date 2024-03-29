package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geomicrodistrict

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.microdistrict.MicrodistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.types.MicrodistrictType

class MicrodistrictElementToGeoMicrodistrictMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) : Mapper<MicrodistrictElement, GeoMicrodistrict> {
    override fun map(input: MicrodistrictElement): GeoMicrodistrict {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.microdistrict_full_types)
        val type = MicrodistrictType.valueOf(input.tags.place.uppercase())
        val resType = resArray.firstOrNull {
            input.tags.nameLoc.contains(it, true) || input.tags.name.contains(it, true)
        }
        return GeoMicrodistrict(
            locality = GeoLocality().also { it.id = input.tags.localityId },
            localityDistrict = input.tags.localityDistrictId?.let { rdId ->
                GeoLocalityDistrict().also { it.id = rdId }
            },
            microdistrictType = resType?.let { MicrodistrictType.entries[resArray.indexOf(it)] } ?: type,
            microdistrictShortName = GeoLocality.shortNameFromName(
                prefix = input.tags.wikidata.ifEmpty { input.tags.gnisId },
                name = input.tags.name
            ),
            microdistrictGeocode = input.tags.geocodeArea.ifEmpty { input.tags.name },
            microdistrictOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            microdistrictName = input.tags.nameLoc.ifEmpty { input.tags.name }
        )
    }
}