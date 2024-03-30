package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geostreet

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.street.StreetElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.types.RoadType

class StreetElementToGeoStreetMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) : Mapper<StreetElement, GeoStreet> {
    override fun map(input: StreetElement): GeoStreet {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_full_types)
        val resType = resArray.firstOrNull { res ->
            res.splitToSequence(';').any {
                input.tags.nameLoc.contains(it, true) || input.tags.name.contains(it, true)
            }
        }
        val streetName = input.tags.nameLoc.ifEmpty { input.tags.name }
            .replace(resType.orEmpty().toRegex(RegexOption.IGNORE_CASE), "").trim()
        return GeoStreet(
            locality = GeoLocality().also { it.id = input.tags.localityId },
            streetHashCode = streetName.hashCode(),
            roadType = resType?.let { RoadType.entries[resArray.indexOf(it)] } ?: RoadType.STREET,
            streetGeocode = input.tags.name,
            streetOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            streetName = streetName
        )
    }
}