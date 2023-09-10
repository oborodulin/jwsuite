package com.oborodulin.jwsuite.domain.usecases.house

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteTerritoryHouseUseCase(
    configuration: Configuration,
    private val housesRepository: HousesRepository
) : UseCase<DeleteTerritoryHouseUseCase.Request, DeleteTerritoryHouseUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return housesRepository.clearTerritory(request.houseId).map { Response }
    }

    data class Request(val houseId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
