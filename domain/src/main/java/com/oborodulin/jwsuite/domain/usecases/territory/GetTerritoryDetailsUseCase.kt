package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetTerritoryDetailsUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryDetailsUseCase.Request, GetTerritoryDetailsUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        combine(combine(
            territoriesRepository.getTerritoryStreets(request.territoryId),
            territoriesRepository.getTerritoryStreetHouses(request.territoryId),
        ) { streets, streetHouses ->
            val details = mutableListOf<TerritoryDetail>()
            streets.forEach {  }
        },combine(
                territoriesRepository.getHouses(request.territoryId),
                territoriesRepository.getEntrances(request.territoryId),
                territoriesRepository.getFloors(request.territoryId),
                territoriesRepository.getRooms(request.territoryId)
            ) { houses, entrances, floors, rooms ->
            })
        {

        }

    val details = mutableListOf<TerritoryDetail>()
    if (streets.isNotEmpty())
    {
        streets.forEach { }
    } else
    {
        houses.forEach { details.add(TerritoryDetail()) }
    }
    //TerritoryDetail
    Response(details)
}

data class Request(val territoryId: UUID) : UseCase.Request
data class Response(val territoryDetails: List<TerritoryDetail>) : UseCase.Response
}