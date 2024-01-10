package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryStreetsUseCase(
    configuration: Configuration, private val territoryStreetsRepository: TerritoryStreetsRepository
) : UseCase<GetTerritoryStreetsUseCase.Request, GetTerritoryStreetsUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        territoryStreetsRepository.getAllByTerritory(request.territoryId).map {
            Response(it)
        }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryStreets: List<TerritoryStreet>) : UseCase.Response
}