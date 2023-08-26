package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryStreetUseCase.Request, GetTerritoryStreetUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getTerritoryStreet(request.territoryStreetId).map {
            Response(it)
        }

    data class Request(val territoryStreetId: UUID) : UseCase.Request
    data class Response(val territoryStreet: TerritoryStreet) : UseCase.Response
}