package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRoomsForTerritoryUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<GetRoomsForTerritoryUseCase.Request, GetRoomsForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = roomsRepository.getAllForTerritory(request.territoryId)
        .map { Response(it) }

    data class Request(val territoryId: UUID) : UseCase.Request
    data class Response(val rooms: List<Room>) : UseCase.Response
}