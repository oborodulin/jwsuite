package com.oborodulin.jwsuite.data_geo.remote.osm.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates

class GeometryToGeoCoordinatesMapper : Mapper<Geometry, GeoCoordinates> {
    override fun map(input: Geometry) = GeoCoordinates(
        latitude = input.coordinates.getOrNull(0),
        longitude = input.coordinates.getOrNull(1)
    )
}