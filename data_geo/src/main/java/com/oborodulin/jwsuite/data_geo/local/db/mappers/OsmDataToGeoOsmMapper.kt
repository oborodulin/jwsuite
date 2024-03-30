package com.oborodulin.jwsuite.data_geo.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.OsmData
import com.oborodulin.jwsuite.domain.model.geo.GeoCoordinates
import com.oborodulin.jwsuite.domain.model.geo.GeoOsm

class OsmDataToGeoOsmMapper : Mapper<OsmData, GeoOsm>, NullableMapper<OsmData, GeoOsm> {
    override fun map(input: OsmData) = GeoOsm(
        geocode = input.geocode,
        osmId = input.osmId,
        coordinates = GeoCoordinates(
            latitude = input.coordinates.latitude,
            longitude = input.coordinates.longitude
        )
    )

    override fun nullableMap(input: OsmData?) = input?.let { map(it) }
}