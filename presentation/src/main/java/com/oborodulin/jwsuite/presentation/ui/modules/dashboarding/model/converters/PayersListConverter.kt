package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.jwsuite.presentation.ui.congregating.model.mappers.PayerListToPayerListItemMapper
import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.home.accounting.domain.usecases.GetPayersUseCase

class PayersListConverter(
    private val mapper: PayerListToPayerListItemMapper
) :
    CommonResultConverter<GetPayersUseCase.Response, List<CongregationListItem>>() {
    override fun convertSuccess(data: GetPayersUseCase.Response) = mapper.map(data.payers)
}