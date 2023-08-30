package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.CongregationsListToCongregationsListItemMapper

class CongregationsListConverter(
    private val mapper: CongregationsListToCongregationsListItemMapper
) :
    CommonResultConverter<GetCongregationsUseCase.Response, List<CongregationsListItem>>() {
    override fun convertSuccess(data: GetCongregationsUseCase.Response) =
        mapper.map(data.congregations)
}