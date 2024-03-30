package com.oborodulin.jwsuite.data_geo.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.OsmData
import com.oborodulin.jwsuite.domain.model.geo.GeoOsm

class GeoOsmToOsmDataMapper : Mapper<GeoOsm, OsmData>, NullableMapper<GeoOsm, OsmData> {
    override fun map(input: GeoOsm) = OsmData(
        geocode = input.geocode,
        osmId = input.osmId,
        coordinates = Coordinates(
            latitude = input.coordinates.latitude, longitude = input.coordinates.longitude
        )
    )

    override fun nullableMap(input: GeoOsm?) = input?.let { map(it) }
}