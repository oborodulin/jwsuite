package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoryStreetsRepository: TerritoryStreetsRepository
) : UseCase<DeleteTerritoryStreetUseCase.Request, DeleteTerritoryStreetUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return territoryStreetsRepository.delete(request.territoryStreetId)
            .map {
                Response
            }
    }

    data class Request(val territoryStreetId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
