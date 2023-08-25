package com.oborodulin.jwsuite.domain.usecases.territory

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetTerritoryDetailsUseCase(
    configuration: Configuration,
    private val ctx: Context,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryDetailsUseCase.Request, GetTerritoryDetailsUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        combine(
            territoriesRepository.getTerritoryStreetHouses(request.territoryId),
            territoriesRepository.getHouses(request.territoryId),
            territoriesRepository.getEntrances(request.territoryId),
            territoriesRepository.getFloors(request.territoryId),
            territoriesRepository.getRooms(request.territoryId)
        ) { territoryStreets, houses, entrances, floors, rooms ->
            val territoryDetails = mutableListOf<TerritoryDetail>()
            territoryStreets.forEach {
                territoryDetails.add(
                    TerritoryDetail(
                        ctx = ctx,
                        territoryStreetId = it.id,
                        street = it.street,
                        isPrivateSector = it.isPrivateSector,
                        estimatedHouses = it.estimatedHouses,
                        isEvenSide = it.isEvenSide,
                        houses = it.houses
                    )
                )
            }
            houses.groupBy({ it.street }) { it }
                .forEach { (street, houses) ->
                    territoryDetails.add(
                        TerritoryDetail(ctx = ctx, street = street, houses = houses)
                    )
                }
            entrances.groupBy({ it.house }) { it }
                .forEach { (house, entrances) ->
                    territoryDetails.add(
                        TerritoryDetail(ctx = ctx, street = house.street, entrances = entrances)
                    )
                }
            Response(territoryDetails)
        }


    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryDetails: List<TerritoryDetail>) : UseCase.Response
}