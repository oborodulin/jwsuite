package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetTerritoryDetailUseCase(
    configuration: Configuration,
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
/*            val territoryStreets = mutableListOf<TerritoryStreet>()
            streets.forEach { street ->
                streetHouses.filter { it.street.id == street.id && (street.isEven == null || (street.isEven && it.houseNum % 2 == 0 || !street.isEven && it.houseNum % 2 == 1)) }
                    .forEach {
                        val territoryStreet = it
                        street.houses.add()
                    }
            }*/

            //TerritoryDetail
            Response(
                TerritoryDetail(
                    territoryStreets = territoryStreets,
                    houses = houses,
                    entrances = entrances,
                    floors = floors,
                    rooms = rooms
                )
            )
        }


    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryDetail: TerritoryDetail) : UseCase.Response
}