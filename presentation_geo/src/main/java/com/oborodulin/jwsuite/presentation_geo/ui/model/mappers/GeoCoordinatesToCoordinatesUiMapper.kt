package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates
import com.oborodulin.jwsuite.presentation_geo.ui.model.CoordinatesUi

class GeoCoordinatesToCoordinatesUiMapper : Mapper<GeoCoordinates, CoordinatesUi>,
    NullableMapper<GeoCoordinates, CoordinatesUi> {
    override fun map(input: GeoCoordinates) = CoordinatesUi(
        latitude = input.latitude,
        longitude = input.longitude
    )

    override fun nullableMap(input: GeoCoordinates?) = input?.let { map(it) }
}