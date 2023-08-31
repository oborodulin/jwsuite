package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper

class LocalityConverter(private val mapper: LocalityToLocalityUiMapper) :
    CommonResultConverter<GetLocalityUseCase.Response, LocalityUi>() {
    override fun convertSuccess(data: GetLocalityUseCase.Response) =
        mapper.map(data.locality)
}