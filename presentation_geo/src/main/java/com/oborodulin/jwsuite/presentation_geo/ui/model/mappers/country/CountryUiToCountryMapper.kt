package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.model.geo.GeoOsm
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.CoordinatesUiToGeoCoordinatesMapper

class CountryUiToCountryMapper(private val mapper: CoordinatesUiToGeoCoordinatesMapper) :
    Mapper<CountryUi, GeoCountry> {
    override fun map(input: CountryUi) = GeoCountry(
        countryCode = input.countryCode,
        osm = GeoOsm(
            geocode = input.countryGeocode,
            osmId = input.countryOsmId,
            coordinates = mapper.map(input.coordinates)
        ),
        countryName = input.countryName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }
}