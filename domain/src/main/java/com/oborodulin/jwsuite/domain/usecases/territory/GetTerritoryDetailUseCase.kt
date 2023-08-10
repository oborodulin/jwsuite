package com.oborodulin.jwsuite.domain.usecases.territory

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetTerritoryDetailUseCase(
    configuration: Configuration,
    private val ctx: Context,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoryDetailUseCase.Request, GetTerritoryDetailUseCase.Response>(
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
                        streetId = it.street.id!!,
                        roadType = it.street.roadType,
                        isPrivateSector = it.isPrivateSector ?: it.street.isPrivateSector,
                        housesQty = it.estimatedHouses,
                        streetName = it.street.streetName,
                        isEven = it.isEven,
                        houses = it.houses
                    )
                )
            }
            houses.groupBy({ it.street }) { it }
                .forEach { (street, houses) ->
                    territoryDetails.add(
                        TerritoryDetail(
                            ctx = ctx,
                            streetId = street.id!!,
                            roadType = street.roadType,
                            isPrivateSector = street.isPrivateSector,
                            housesQty = street.estimatedHouses,
                            streetName = street.streetName,
                            houses = houses
                        )
                    )
                }
            houses.groupBy({ it.street }) { it }
                .forEach { (street, houses) ->
                    territoryDetails.add(
                        TerritoryDetail(
                            ctx = ctx,
                            streetId = street.id!!,
                            roadType = street.roadType,
                            isPrivateSector = street.isPrivateSector,
                            housesQty = street.estimatedHouses,
                            streetName = street.streetName,
                            houses = houses
                        )
                    )
                }
            Response(territoryDetails)
        }


    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryDetails: List<TerritoryDetail>) : UseCase.Response
}