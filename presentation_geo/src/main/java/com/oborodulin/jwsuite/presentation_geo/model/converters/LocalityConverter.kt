package com.oborodulin.jwsuite.presentation_geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.presentation_geo.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.model.mappers.locality.LocalityToLocalityUiMapper

class LocalityConverter(private val mapper: LocalityToLocalityUiMapper) :
    CommonResultConverter<GetLocalityUseCase.Response, LocalityUi>() {
    override fun convertSuccess(data: GetLocalityUseCase.Response) =
        mapper.map(data.locality)
}