package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRoomsUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<GetRoomsUseCase.Request, GetRoomsUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.territoryId) {
        null -> when (request.houseId) {
            null -> roomsRepository.getAll()
            else -> roomsRepository.getAllByHouse(request.houseId)
        }

        else -> roomsRepository.getAllByTerritory(request.territoryId)
    }.map {
        Response(it)
    }

    data class Request(val houseId: UUID? = null, val territoryId: UUID? = null) : UseCase.Request
    data class Response(val rooms: List<Room>) : UseCase.Response
}