package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import java.util.UUID

class HandOutTerritoriesUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<HandOutTerritoriesUseCase.Request, HandOutTerritoriesUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.handOutTerritories(
            request.memberId, request.territoryIds, request.receivingDate
        ).map {
            Response(it)
        }.catch { throw UseCaseException.HandOutTerritoryException(it) }
    }

    data class Request(
        val memberId: UUID, val territoryIds: List<UUID> = emptyList(),
        val receivingDate: OffsetDateTime
    ) : UseCase.Request

    data class Response(val ids: List<UUID>) : UseCase.Response
}
