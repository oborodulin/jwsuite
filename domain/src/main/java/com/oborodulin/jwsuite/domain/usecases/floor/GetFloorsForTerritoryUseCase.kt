package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryWithFloors
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetFloorsForTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository,
    private val floorsRepository: FloorsRepository
) : UseCase<GetFloorsForTerritoryUseCase.Request, GetFloorsForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = combine(
        territoriesRepository.get(request.territoryId),
        floorsRepository.getAllForTerritory(request.territoryId)
    ) { territory, floors ->
        Response(
            TerritoryWithFloors(territory = territory, floors = floors)
        )
    }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryWithFloors: TerritoryWithFloors) : UseCase.Response
}