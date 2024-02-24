package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates
import com.oborodulin.jwsuite.presentation_geo.ui.model.CoordinatesUi

class CoordinatesUiToGeoCoordinatesMapper : Mapper<CoordinatesUi, GeoCoordinates>,
    NullableMapper<CoordinatesUi, GeoCoordinates> {
    override fun map(input: CoordinatesUi) = GeoCoordinates(
        latitude = input.latitude,
        longitude = input.longitude
    )

    override fun nullableMap(input: CoordinatesUi?) = input?.let { map(it) }
}