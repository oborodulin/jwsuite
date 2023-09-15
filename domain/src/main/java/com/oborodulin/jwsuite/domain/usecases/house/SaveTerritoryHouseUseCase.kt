package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.UUID

class SaveTerritoryHouseUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<SaveTerritoryHouseUseCase.Request, SaveTerritoryHouseUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return housesRepository.setTerritory(request.houseId, request.territoryId)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryHouseSaveException(it) }
    }

    data class Request(val houseId: UUID, val territoryId: UUID) : UseCase.Request
    data class Response(val house: House) : UseCase.Response
}
