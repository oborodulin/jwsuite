package com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet

class GeoStreetViewToGeoStreetMapper(
    private val localityMapper: GeoLocalityViewToGeoLocalityMapper,
    private val streetMapper: StreetViewToGeoStreetMapper
) : Mapper<GeoStreetView, GeoStreet> {
    override fun map(input: GeoStreetView) =
        streetMapper.map(input.street).also { it.locality = localityMapper.map(input.locality) }
}