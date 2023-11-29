package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper

class SaveCongregationConverter(private val mapper: CongregationToCongregationUiMapper) :
    CommonResultConverter<SaveCongregationUseCase.Response, CongregationUi>() {
    override fun convertSuccess(data: SaveCongregationUseCase.Response) =
        mapper.map(data.congregation)
}