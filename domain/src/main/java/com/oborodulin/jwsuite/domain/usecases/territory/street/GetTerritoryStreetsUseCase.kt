package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryStreetsUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryStreetsUseCase.Request, GetTerritoryStreetsUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getTerritoryStreets(request.territoryId).map {
            Response(it)
        }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryStreets: List<TerritoryStreet>) : UseCase.Response
}