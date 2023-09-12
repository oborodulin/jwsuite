package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetHousesForTerritoryUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<GetHousesForTerritoryUseCase.Request, GetHousesForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = housesRepository.getAllForTerritory(request.territoryId)
        .map { Response(it) }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val houses: List<House>) : UseCase.Response
}