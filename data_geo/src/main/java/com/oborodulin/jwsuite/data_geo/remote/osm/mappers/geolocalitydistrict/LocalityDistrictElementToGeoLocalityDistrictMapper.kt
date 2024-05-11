package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocalitydistrict

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityDistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class LocalityDistrictElementToGeoLocalityDistrictMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) : Mapper<LocalityDistrictElement, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictElement): GeoLocalityDistrict {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.region_district_full_types)
        val resType = resArray.firstOrNull {
            input.tags.nameLoc.contains(it, true) || input.tags.name.contains(it, true)
        }
        return GeoLocalityDistrict(
            locality = GeoLocality().also { it.id = input.tags.localityId },
            districtShortName = GeoLocalityDistrict.shortNameFromName(
                prefix = input.tags.wikidata.ifEmpty { input.tags.gnisId },
                name = input.tags.name
            ),
            districtGeocode = input.tags.geocodeArea.ifEmpty { input.tags.name },
            districtOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            districtName = input.tags.nameLoc.ifEmpty { input.tags.name }
                .replace(resType.orEmpty().toRegex(RegexOption.IGNORE_CASE), "").trim()
        )
    }
}