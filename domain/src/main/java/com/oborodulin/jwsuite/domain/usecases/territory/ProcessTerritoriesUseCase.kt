package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import java.util.UUID

class ProcessTerritoriesUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<ProcessTerritoriesUseCase.Request, ProcessTerritoriesUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.processTerritories(request.territoryIds, request.deliveryDate)
            .map {
                Response(it)
            }.catch { throw UseCaseException.ProcessTerritoriesException(it) }
    }

    data class Request(
        val territoryIds: List<UUID> = emptyList(), val deliveryDate: OffsetDateTime
    ) : UseCase.Request

    data class Response(val ids: List<UUID>) : UseCase.Response
}
