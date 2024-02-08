package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryTotals
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteTerritoryTotalsUseCase(
    configuration: Configuration, private val territoriesRepository: TerritoriesRepository
) : UseCase<GetFavoriteTerritoryTotalsUseCase.Request, GetFavoriteTerritoryTotalsUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getFavoriteTotals().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val territoryTotals: TerritoryTotals?) : UseCase.Response
}