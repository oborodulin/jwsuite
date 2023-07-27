package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryLocationsUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryLocationsUseCase.Request, GetTerritoryLocationsUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getCongregationTerritoryLocations(
            isPrivateSector = request.isPrivateSector,
            congregationId = request.congregationId
        ).map {
            Response(it)
        }

    data class Request(
        val congregationId: UUID? = null, val isPrivateSector: Boolean = false
    ) : UseCase.Request

    data class Response(val territoryLocations: List<TerritoryLocation>) : UseCase.Response
}