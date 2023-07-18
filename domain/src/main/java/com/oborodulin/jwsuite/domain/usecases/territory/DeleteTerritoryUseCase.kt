package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<DeleteTerritoryUseCase.Request, DeleteTerritoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.deleteById(request.territoryId)
            .map {
                Response
            }
    }

    data class Request(val territoryId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
