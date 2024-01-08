package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetWithTerritoryAndStreets
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID

private const val TAG = "Domain.GetTerritoryStreetUseCase"

class GetTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository,
    private val territoryStreetsRepository: TerritoryStreetsRepository,
    private val housesRepository: HousesRepository
) : UseCase<GetTerritoryStreetUseCase.Request, GetTerritoryStreetUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.territoryStreetId) {
        //Timber.tag(TAG).d("process(...) called: request = %s", request)
        null -> combine(
            territoriesRepository.get(request.territoryId),
            territoryStreetsRepository.getTerritoryStreets(request.territoryId)
        ) { territory, territoryStreets ->
            Timber.tag(TAG).d(
                "process -> combine(...): territory = %s; territoryStreets = %s",
                territory, territoryStreets
            )
            TerritoryStreetWithTerritoryAndStreets(
                territory = territory,
                streets = territoryStreets.map { it.street }
            )
        }

        else -> combine(
            territoriesRepository.get(request.territoryId),
            territoryStreetsRepository.getTerritoryStreet(request.territoryStreetId),
            territoryStreetsRepository.getTerritoryStreets(request.territoryId),
            housesRepository.isTerritoryStreetExistsHouses(request.territoryStreetId)
        ) { territory, territoryStreet, territoryStreets, isExistsHouses ->
            Timber.tag(TAG).d(
                "process -> combine(...): territory = %s; territoryStreet = %s; territoryStreets = %s; isExistsHouses = %s",
                territory, territoryStreet, territoryStreets, isExistsHouses
            )
            TerritoryStreetWithTerritoryAndStreets(
                territoryStreet = territoryStreet.copy(isExistsHouses = isExistsHouses),
                territory = territory,
                streets = territoryStreets.map { it.street }
            )
        }
    }.map {
        Response(it)
    }

    data class Request(val territoryId: UUID, val territoryStreetId: UUID? = null) : UseCase.Request
    data class Response(val territoryStreetWithTerritoryAndStreets: TerritoryStreetWithTerritoryAndStreets) :
        UseCase.Response
}