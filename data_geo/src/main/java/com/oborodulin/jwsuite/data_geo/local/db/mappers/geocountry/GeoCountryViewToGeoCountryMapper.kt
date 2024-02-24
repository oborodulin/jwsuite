package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.CoordinatesToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry

class GeoCountryViewToGeoCountryMapper(private val mapper: CoordinatesToGeoCoordinatesMapper) :
    Mapper<GeoCountryView, GeoCountry>, NullableMapper<GeoCountryView, GeoCountry> {
    override fun map(input: GeoCountryView) = GeoCountry(
        countryCode = input.data.countryCode,
        countryGeocode = input.data.countryGeocode,
        countryOsmId = input.data.countryOsmId,
        coordinates = mapper.map(input.data.coordinates),
        countryName = input.tl.countryName
    ).also {
        it.id = input.data.countryId
        it.tlId = input.tl.countryTlId
    }

    override fun nullableMap(input: GeoCountryView?) = input?.let { map(it) }
}