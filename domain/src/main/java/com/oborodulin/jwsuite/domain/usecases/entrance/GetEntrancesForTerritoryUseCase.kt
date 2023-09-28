package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryWithEntrances
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetEntrancesForTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository,
    private val entrancesRepository: EntrancesRepository
) : UseCase<GetEntrancesForTerritoryUseCase.Request, GetEntrancesForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = combine(
        territoriesRepository.get(request.territoryId),
        entrancesRepository.getAllForTerritory(request.territoryId)
    ) { territory, entrances ->
        Response(
            TerritoryWithEntrances(territory = territory, entrances = entrances)
        )
    }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryWithEntrances: TerritoryWithEntrances) : UseCase.Response
}