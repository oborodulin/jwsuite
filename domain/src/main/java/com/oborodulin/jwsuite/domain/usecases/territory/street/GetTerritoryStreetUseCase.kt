package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryStreetWithTerritoryAndStreets
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryStreetUseCase.Request, GetTerritoryStreetUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        combine(
            territoriesRepository.get(request.territoryId),
            territoriesRepository.getTerritoryStreet(request.territoryStreetId),
            territoriesRepository.getTerritoryStreets(request.territoryId)
        ) { territory, territoryStreet, territoryStreets ->
            TerritoryStreetWithTerritoryAndStreets(
                territoryStreet = territoryStreet,
                territory = territory,
                streets = territoryStreets.map { it.street }
            )
        }.map {
            Response(it)
        }

    data class Request(val territoryId: UUID, val territoryStreetId: UUID? = null) : UseCase.Request
    data class Response(val territoryStreetWithTerritoryAndStreets: TerritoryStreetWithTerritoryAndStreets) :
        UseCase.Response
}