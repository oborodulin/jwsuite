package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardingUi

class FavoriteCongregationConverter(
    private val mapper: CongregationToCongregationUiMapper
) : CommonResultConverter<GetFavoriteCongregationUseCase.Response, DashboardingUi>() {
    override fun convertSuccess(data: GetFavoriteCongregationUseCase.Response) =
        DashboardingUi(favoriteCongregation = mapper.nullableMap(data.congregation))
}