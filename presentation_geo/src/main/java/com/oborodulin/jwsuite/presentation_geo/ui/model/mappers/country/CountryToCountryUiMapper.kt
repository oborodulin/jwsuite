package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper

class CountryToCountryUiMapper(private val mapper: GeoCoordinatesToCoordinatesUiMapper) :
    Mapper<GeoCountry, CountryUi>, NullableMapper<GeoCountry, CountryUi> {
    override fun map(input: GeoCountry) = CountryUi(
        countryCode = input.countryCode,
        countryGeocode = input.osm.geocode,
        countryOsmId = input.osm.osmId,
        coordinates = mapper.map(input.osm.coordinates),
        countryName = input.countryName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }

    override fun nullableMap(input: GeoCountry?) = input?.let { map(it) }
}