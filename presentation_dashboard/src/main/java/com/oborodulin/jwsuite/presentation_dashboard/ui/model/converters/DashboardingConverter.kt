package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.dashboard.GetDashboardInfoUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardingUi
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.DashboardToDashboardingUiMapper

class DashboardingConverter(private val mapper: DashboardToDashboardingUiMapper) :
    CommonResultConverter<GetDashboardInfoUseCase.Response, DashboardingUi>() {
    override fun convertSuccess(data: GetDashboardInfoUseCase.Response) = mapper.map(data.dashboard)
}