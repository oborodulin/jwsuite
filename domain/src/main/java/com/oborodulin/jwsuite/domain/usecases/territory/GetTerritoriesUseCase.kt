package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.util.TerritoryDistrictType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetTerritoriesUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetTerritoriesUseCase.Request, GetTerritoriesUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        territoriesRepository.getCongregationTerritories(request.congregationId).map {
            Response(it)
        }

    data class Request(
        val congregationId: UUID? = null,
        val territoryProcessType: TerritoryProcessType,
        val territoryDistrictType: TerritoryDistrictType,
        val districtId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : UseCase.Request

    data class Response(val territories: List<Territory>) : UseCase.Response
}