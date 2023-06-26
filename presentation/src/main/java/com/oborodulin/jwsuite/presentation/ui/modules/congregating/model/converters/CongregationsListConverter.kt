package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationsListToCongregationsListItemMapper

class CongregationsListConverter(
    private val mapper: CongregationsListToCongregationsListItemMapper
) :
    CommonResultConverter<GetCongregationsUseCase.Response, List<CongregationsListItem>>() {
    override fun convertSuccess(data: GetCongregationsUseCase.Response) =
        mapper.map(data.congregations)
}