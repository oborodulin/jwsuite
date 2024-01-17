package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geostreet

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.services.csv.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv

class GeoStreetViewToGeoStreetMapper(
    private val ctx: Context, private val localityMapper: GeoLocalityViewToGeoLocalityMapper,
) : Mapper<GeoStreetView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv> {
    override fun map(input: GeoStreetView): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv {
        val street = com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoStreetCsv(
            ctx = ctx,
            locality = localityMapper.map(input.locality),
            streetHashCode = input.street.data.streetHashCode,
            roadType = input.street.data.roadType,
            isPrivateSector = input.street.data.isStreetPrivateSector,
            estimatedHouses = input.street.data.estStreetHouses,
            streetName = input.street.tl.streetName
        )
        street.id = input.street.data.streetId
        street.tlId = input.street.tl.streetTlId
        return street
    }
}