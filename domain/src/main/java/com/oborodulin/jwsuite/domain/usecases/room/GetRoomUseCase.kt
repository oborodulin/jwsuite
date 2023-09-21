package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRoomUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) :
    UseCase<GetRoomUseCase.Request, GetRoomUseCase.Response>(configuration) {
    override fun process(request: Request) = roomsRepository.get(request.roomId).map {
        Response(it)
    }

    data class Request(val roomId: UUID) : UseCase.Request
    data class Response(val room: Room) : UseCase.Response
}