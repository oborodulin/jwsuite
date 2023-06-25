package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.congregating.model.mappers.PayerToPayerUiMapper
import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.home.domain.usecases.GetPayerUseCase

class PayerConverter(
    private val mapper: PayerToPayerUiMapper
) :
    CommonResultConverter<GetPayerUseCase.Response, CongregationUi>() {
    override fun convertSuccess(data: GetPayerUseCase.Response) = mapper.map(data.payer)
}