package com.oborodulin.jwsuite.domain.usecases.entrance

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryEntrancesUseCase(
    configuration: Configuration, private val entrancesRepository: EntrancesRepository
) : UseCase<SaveTerritoryEntrancesUseCase.Request, SaveTerritoryEntrancesUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return entrancesRepository.setTerritory(request.entranceIds, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryEntranceSaveException(it) }
    }

    data class Request(val entranceIds: List<UUID> = emptyList(), val territoryId: UUID) :
        UseCase.Request

    data class Response(val entranceIds: List<UUID>) : UseCase.Response
}
