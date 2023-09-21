package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteRoomUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<DeleteRoomUseCase.Request, DeleteRoomUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return roomsRepository.deleteById(request.roomId).map { Response }
    }

    data class Request(val roomId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
