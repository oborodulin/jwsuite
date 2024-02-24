package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geocountry.SaveCountryUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryToCountryUiMapper

class SaveCountryConverter(private val mapper: CountryToCountryUiMapper) :
    CommonResultConverter<SaveCountryUseCase.Response, CountryUi>() {
    override fun convertSuccess(data: SaveCountryUseCase.Response) = mapper.map(data.country)
}