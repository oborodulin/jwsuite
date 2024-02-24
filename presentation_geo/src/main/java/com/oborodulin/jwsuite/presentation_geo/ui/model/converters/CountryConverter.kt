package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountryUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountryUiMapper

class CountryConverter(private val mapper: CountryToCountryUiMapper) :
    CommonResultConverter<GetCountryUseCase.Response, CountryUi>() {
    override fun convertSuccess(data: GetCountryUseCase.Response) = mapper.map(data.country)
}