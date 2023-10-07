package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryUseCase.Request, GetTerritoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.get(request.territoryId).map {
            Response(it)
        }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territory: Territory) : UseCase.Response
}