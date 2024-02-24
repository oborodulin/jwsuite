package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountriesUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountriesListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountriesListToCountriesListItemMapper

class CountriesListConverter(private val mapper: CountriesListToCountriesListItemMapper) :
    CommonResultConverter<GetCountriesUseCase.Response, List<CountriesListItem>>() {
    override fun convertSuccess(data: GetCountriesUseCase.Response) =
        mapper.map(data.countries)
}