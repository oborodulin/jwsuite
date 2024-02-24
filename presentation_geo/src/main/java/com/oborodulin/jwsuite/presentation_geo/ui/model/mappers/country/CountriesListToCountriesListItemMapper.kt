package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountriesListItem

class CountriesListToCountriesListItemMapper(mapper: CountryToCountriesListItemMapper) :
    ListMapperImpl<GeoCountry, CountriesListItem>(mapper)