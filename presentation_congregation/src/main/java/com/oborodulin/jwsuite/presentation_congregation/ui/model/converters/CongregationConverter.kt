package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.CongregationToCongregationUiMapper

class CongregationConverter(
    private val mapper: CongregationToCongregationUiMapper
) :
    CommonResultConverter<GetCongregationUseCase.Response, CongregationUi>() {
    override fun convertSuccess(data: GetCongregationUseCase.Response) =
        mapper.map(data.congregation)
}