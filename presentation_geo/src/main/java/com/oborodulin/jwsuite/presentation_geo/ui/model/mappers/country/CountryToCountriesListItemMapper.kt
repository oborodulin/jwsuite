package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountriesListItem
import java.util.UUID

class CountryToCountriesListItemMapper : Mapper<GeoCountry, CountriesListItem> {
    override fun map(input: GeoCountry) = CountriesListItem(
        id = input.id ?: UUID.randomUUID(),
        countryCode = input.countryCode,
        countryName = input.countryName
    )
}