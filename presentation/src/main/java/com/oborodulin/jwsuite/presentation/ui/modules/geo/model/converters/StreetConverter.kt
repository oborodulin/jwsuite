package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.street.StreetToStreetUiMapper

class StreetConverter(private val mapper: StreetToStreetUiMapper) :
    CommonResultConverter<GetStreetUseCase.Response, StreetUi>() {
    override fun convertSuccess(data: GetStreetUseCase.Response) =
        mapper.map(data.street)
}