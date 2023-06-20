package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.GeoStreet

class GeoStreetViewToGeoStreetMapper : Mapper<GeoStreetView, GeoStreet> {
    override fun map(input: GeoStreetView): GeoStreet {
        val street = GeoStreet(
            localityId = input.data.localitiesId,
            streetHashCode = input.data.streetHashCode,
            roadType = input.data.roadType,
            isPrivateSector = input.data.isPrivateSector,
            estimatedHouses = input.data.estimatedHouses,
            streetName = input.tl.streetName
        )
        street.id = input.data.streetId
        street.tlId = input.tl.streetTlId
        return street
    }
}