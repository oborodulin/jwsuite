package com.oborodulin.jwsuite.domain.usecases.territory.street

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveTerritoryStreetUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<SaveTerritoryStreetUseCase.Request, SaveTerritoryStreetUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return territoriesRepository.saveTerritoryStreet(request.territoryStreet)
            .map {
                Response(it)
            }.catch { throw UseCaseException.TerritoryStreetSaveException(it) }
    }

    data class Request(val territoryStreet: TerritoryStreet) : UseCase.Request
    data class Response(val territoryStreet: TerritoryStreet) : UseCase.Response
}
