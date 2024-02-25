package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality

class LocalityViewToGeoLocalityMapper(
    private val ctx: Context,
    private val mapper: CoordinatesToGeoCoordinatesMapper
) : Mapper<LocalityView, GeoLocality> {
    override fun map(input: LocalityView) = GeoLocality(
        ctx = ctx,
        localityCode = input.data.localityCode,
        localityType = input.data.localityType,
        localityGeocode = input.data.localityGeocode,
        localityOsmId = input.data.localityOsmId,
        coordinates = mapper.map(input.data.coordinates),
        localityShortName = input.tl.localityShortName,
        localityName = input.tl.localityName
    ).also {
        it.id = input.data.localityId
        it.tlId = input.tl.localityTlId
    }
}