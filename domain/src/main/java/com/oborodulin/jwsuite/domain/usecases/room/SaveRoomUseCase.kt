package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveRoomUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<SaveRoomUseCase.Request, SaveRoomUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return roomsRepository.save(request.room)
            .map {
                Response(it)
            }.catch { throw UseCaseException.RoomSaveException(it) }
    }

    data class Request(val room: Room) : UseCase.Request
    data class Response(val room: Room) : UseCase.Response
}
