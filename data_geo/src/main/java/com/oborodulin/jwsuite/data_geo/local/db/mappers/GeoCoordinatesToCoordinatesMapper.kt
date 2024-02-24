package com.oborodulin.jwsuite.data_geo.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates

class GeoCoordinatesToCoordinatesMapper : Mapper<GeoCoordinates, Coordinates>,
    NullableMapper<GeoCoordinates, Coordinates> {
    override fun map(input: GeoCoordinates) = Coordinates(
        latitude = input.latitude,
        longitude = input.longitude
    )

    override fun nullableMap(input: GeoCoordinates?) = input?.let { map(it) }
}