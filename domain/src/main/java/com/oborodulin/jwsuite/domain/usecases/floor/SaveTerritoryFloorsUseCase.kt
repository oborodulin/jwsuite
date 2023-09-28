package com.oborodulin.jwsuite.domain.usecases.floor

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryFloorsUseCase(
    configuration: Configuration, private val floorsRepository: FloorsRepository
) : UseCase<SaveTerritoryFloorsUseCase.Request, SaveTerritoryFloorsUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return floorsRepository.setTerritory(request.floorIds, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryFloorSaveException(it) }
    }

    data class Request(val floorIds: List<UUID> = emptyList(), val territoryId: UUID) :
        UseCase.Request

    data class Response(val floorIds: List<UUID>) : UseCase.Response
}
