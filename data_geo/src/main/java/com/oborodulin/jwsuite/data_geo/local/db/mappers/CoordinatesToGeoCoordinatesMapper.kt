package com.oborodulin.jwsuite.data_geo.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates

class CoordinatesToGeoCoordinatesMapper : Mapper<Coordinates, GeoCoordinates>,
    NullableMapper<Coordinates, GeoCoordinates> {
    override fun map(input: Coordinates) = GeoCoordinates(
        latitude = input.latitude,
        longitude = input.longitude
    )

    override fun nullableMap(input: Coordinates?) = input?.let { map(it) }
}