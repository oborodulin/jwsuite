package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.TerritoryWithRooms
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetRoomsForTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository,
    private val roomsRepository: RoomsRepository
) : UseCase<GetRoomsForTerritoryUseCase.Request, GetRoomsForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = combine(
        territoriesRepository.get(request.territoryId),
        roomsRepository.getAllForTerritory(request.territoryId)
    ) { territory, rooms -> Response(TerritoryWithRooms(territory = territory, rooms = rooms)) }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val territoryWithRooms: TerritoryWithRooms) : UseCase.Response
}