package com.oborodulin.jwsuite.domain.usecases.room

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryRoomsUseCase(
    configuration: Configuration,
    private val roomsRepository: RoomsRepository
) : UseCase<SaveTerritoryRoomsUseCase.Request, SaveTerritoryRoomsUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return roomsRepository.setTerritory(request.roomIds, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryRoomSaveException(it) }
    }

    data class Request(val roomIds: List<UUID> = emptyList(), val territoryId: UUID) :
        UseCase.Request

    data class Response(val roomIds: List<UUID>) : UseCase.Response
}
