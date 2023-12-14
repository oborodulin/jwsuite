package com.oborodulin.jwsuite.domain.usecases.territory

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.types.TerritoryProcessType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetProcessAndLocationTerritoriesUseCase(
    configuration: Configuration,
    private val territoriesRepository: TerritoriesRepository
) : UseCase<GetProcessAndLocationTerritoriesUseCase.Request, GetProcessAndLocationTerritoriesUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> =
        combine(
            territoriesRepository.getTerritories(
                territoryProcessType = request.territoryProcessType,
                territoryLocationType = request.territoryLocationType,
                locationId = request.locationId,
                isPrivateSector = request.isPrivateSector,
                congregationId = request.congregationId
            ),
            territoriesRepository.getTerritoryStreetNamesAndHouseNums()
        ) { territories, territoryStreetNamesAndHouseNums ->
            territories.map { territory ->
                val streetNamesAndHouseNums =
                    territoryStreetNamesAndHouseNums.firstOrNull { it.territoryId == territory.id }
                streetNamesAndHouseNums?.let {
                    territory.streetNames = it.streetNames
                    territory.houseNums = it.houseFullNums
                }
                territory
            }
        }.map {
            Response(it)
        }

    data class Request(
        val congregationId: UUID? = null,
        val territoryProcessType: TerritoryProcessType,
        val territoryLocationType: TerritoryLocationType,
        val locationId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : UseCase.Request

    data class Response(val territories: List<Territory>) : UseCase.Response
}