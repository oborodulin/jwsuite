package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationListToCongregationListItemMapper

class RegionsListConverter(
    private val mapper: CongregationListToCongregationListItemMapper
) :
    CommonResultConverter<GetCongregationsUseCase.Response, List<CongregationListItem>>() {
    override fun convertSuccess(data: GetCongregationsUseCase.Response) =
        mapper.map(data.congregations)
}