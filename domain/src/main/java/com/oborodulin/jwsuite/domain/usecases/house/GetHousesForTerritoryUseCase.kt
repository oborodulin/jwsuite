package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryWithHouses
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetHousesForTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository,
    private val housesRepository: HousesRepository
) : UseCase<GetHousesForTerritoryUseCase.Request, GetHousesForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = combine(
        territoriesRepository.get(request.territoryId),
        housesRepository.getAllForTerritory(request.territoryId)
    ) { territory, houses -> Response(TerritoryWithHouses(territory = territory, houses = houses)) }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryWithHouses: TerritoryWithHouses) : UseCase.Response
}