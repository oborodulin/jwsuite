package com.oborodulin.jwsuite.domain.usecases

import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase

data class DashboardingUseCases(
    val getFavoriteCongregationUseCase: GetFavoriteCongregationUseCase
)
