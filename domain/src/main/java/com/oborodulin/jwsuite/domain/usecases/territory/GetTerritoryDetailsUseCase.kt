package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryDetailsUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryDetailsUseCase.Request, GetTerritoryDetailsUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        combine()
        territoriesRepository.getCongregationTerritoryLocations(
            isPrivateSector = request.isPrivateSector,
            congregationId = request.territoryId
        ).map {
            Response(it)
        }

    data class Request(val territoryId: UUID) : UseCase.Request

    data class Response(val territoryDetails: List<TerritoryDetail>) : UseCase.Response
}