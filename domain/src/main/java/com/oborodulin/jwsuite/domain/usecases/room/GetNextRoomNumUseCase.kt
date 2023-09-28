package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextRoomNumUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<GetNextRoomNumUseCase.Request, GetNextRoomNumUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        roomsRepository.getNextNum(request.houseId).map { Response(it) }

    data class Request(val houseId: UUID) : UseCase.Request
    data class Response(val roomNum: Int) : UseCase.Response
}