package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetNextTerritoryNumUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetNextTerritoryNumUseCase.Request, GetNextTerritoryNumUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getNextNum(
            request.congregationId, request.territoryCategoryId
        ).map {
            Response(it)
        }

    data class Request(val congregationId: UUID, val territoryCategoryId: UUID) : UseCase.Request
    data class Response(val territoryNum: Int) : UseCase.Response
}