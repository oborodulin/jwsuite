package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<DeleteTerritoryStreetUseCase.Request, DeleteTerritoryStreetUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.deleteTerritoryStreetById(request.territoryStreetId)
            .map {
                Response
            }
    }

    data class Request(val territoryStreetId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
