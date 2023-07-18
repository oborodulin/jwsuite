package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveTerritoryUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<SaveTerritoryUseCase.Request, SaveTerritoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.save(request.territory)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritorySaveException(it) }
    }

    data class Request(val territory: Territory) : UseCase.Request
    data class Response(val territory: Territory) : UseCase.Response
}
