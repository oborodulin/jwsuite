package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryRoomUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<SaveTerritoryRoomUseCase.Request, SaveTerritoryRoomUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return roomsRepository.setTerritory(request.roomId, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryRoomSaveException(it) }
    }

    data class Request(val roomId: UUID, val territoryId: UUID) : UseCase.Request
    data class Response(val room: Room) : UseCase.Response
}
