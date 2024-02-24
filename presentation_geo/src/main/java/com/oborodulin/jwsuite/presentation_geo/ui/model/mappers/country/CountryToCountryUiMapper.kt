package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.GeoCoordinatesToCoordinatesUiMapper

class CountryToCountryUiMapper(private val mapper: GeoCoordinatesToCoordinatesUiMapper) :
    Mapper<GeoCountry, CountryUi> {
    override fun map(input: GeoCountry) = CountryUi(
        countryCode = input.countryCode,
        countryGeocode = input.countryGeocode,
        countryOsmId = input.countryOsmId,
        coordinates = mapper.map(input.coordinates),
        countryName = input.countryName
    ).also { it.id = input.id }
}