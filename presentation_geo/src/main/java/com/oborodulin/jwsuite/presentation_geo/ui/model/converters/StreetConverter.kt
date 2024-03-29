package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper

class StreetConverter(private val mapper: StreetToStreetUiMapper) :
    CommonResultConverter<GetStreetUseCase.Response, StreetUi>() {
    override fun convertSuccess(data: GetStreetUseCase.Response) =
        mapper.map(data.street)
}