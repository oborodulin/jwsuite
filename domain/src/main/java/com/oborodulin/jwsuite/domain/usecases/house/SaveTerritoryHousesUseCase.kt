package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryHousesUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<SaveTerritoryHousesUseCase.Request, SaveTerritoryHousesUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return housesRepository.setTerritory(request.houseIds, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryHouseSaveException(it) }
    }

    data class Request(val houseIds: List<UUID> = emptyList(), val territoryId: UUID) : UseCase.Request
    data class Response(val houseIds: List<UUID>) : UseCase.Response
}
