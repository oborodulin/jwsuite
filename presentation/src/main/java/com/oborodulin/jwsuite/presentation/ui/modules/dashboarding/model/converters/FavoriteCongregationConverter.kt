package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.DashboardingUi

class FavoriteCongregationConverter(
    private val mapper: CongregationToCongregationUiMapper
) : CommonResultConverter<GetFavoriteCongregationUseCase.Response, DashboardingUi>() {
    override fun convertSuccess(data: GetFavoriteCongregationUseCase.Response) =
        DashboardingUi(favoriteCongregation = mapper.map(data.congregation))
}