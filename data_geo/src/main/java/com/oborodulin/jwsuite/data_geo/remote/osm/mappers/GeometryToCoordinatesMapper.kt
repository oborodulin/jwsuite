package com.oborodulin.jwsuite.data_geo.remote.osm.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import java.math.BigDecimal

class GeometryToCoordinatesMapper : Mapper<Geometry, Coordinates> {
    override fun map(input: Geometry) = Coordinates(
        latitude = input.coordinates.getOrNull(0) ?: BigDecimal.ZERO,
        longitude = input.coordinates.getOrNull(1) ?: BigDecimal.ZERO
    )
}