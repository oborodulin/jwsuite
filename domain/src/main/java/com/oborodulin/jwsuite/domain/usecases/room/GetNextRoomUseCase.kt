package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextRoomUseCase(
    configuration: Configuration, private val roomsRepository: RoomsRepository
) : UseCase<GetNextRoomUseCase.Request, GetNextRoomUseCase.Response>(configuration) {
    override fun process(request: Request) = roomsRepository.getNext(request.roomId).map {
        Response(it)
    }

    data class Request(val roomId: UUID) : UseCase.Request
    data class Response(val room: Room?) : UseCase.Response
}