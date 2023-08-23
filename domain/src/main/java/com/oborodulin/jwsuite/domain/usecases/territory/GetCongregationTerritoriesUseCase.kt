package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetCongregationTerritoriesUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetCongregationTerritoriesUseCase.Request, GetCongregationTerritoriesUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getCongregationTerritories(request.congregationId).map {
            Response(it)
        }

    data class Request(val congregationId: UUID?) : UseCase.Request
    data class Response(val territories: List<Territory>) : UseCase.Response
}