package com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet

class StreetViewToGeoStreetMapper(
    private val ctx: Context,
    private val mapper: CoordinatesToGeoCoordinatesMapper
) : Mapper<StreetView, GeoStreet> {
    override fun map(input: StreetView) = GeoStreet(
        ctx = ctx,
        streetHashCode = input.data.streetHashCode,
        roadType = input.data.roadType,
        isPrivateSector = input.data.isStreetPrivateSector,
        estimatedHouses = input.data.estStreetHouses,
        streetGeocode = input.data.streetGeocode,
        streetOsmId = input.data.streetOsmId,
        coordinates = mapper.map(input.data.coordinates),
        streetName = input.tl.streetName
    ).also {
        it.id = input.data.streetId
        it.tlId = input.tl.streetTlId
    }
}