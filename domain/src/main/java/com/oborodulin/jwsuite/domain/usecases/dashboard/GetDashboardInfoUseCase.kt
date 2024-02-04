package com.oborodulin.jwsuite.domain.usecases.dashboard

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Dashboard
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDashboardInfoUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetDashboardInfoUseCase.Request, GetDashboardInfoUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> =
        combine(
            congregationsRepository.getFavorite(),
            congregationsRepository.getFavoriteTotals()
        ) { congregation, congregationTotals ->
            Response(
                Dashboard(
                    favoriteCongregation = congregation,
                    congregationTotals = congregationTotals
                )
            )
        }

    data object Request : UseCase.Request
    data class Response(val dashboard: Dashboard) : UseCase.Response
}