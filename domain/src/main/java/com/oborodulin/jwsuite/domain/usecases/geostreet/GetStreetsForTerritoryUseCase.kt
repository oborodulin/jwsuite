package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetStreetsForTerritoryUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository
) : UseCase<GetStreetsForTerritoryUseCase.Request, GetStreetsForTerritoryUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> =
        streetsRepository.getAllForTerritory(
            request.localityId, request.localityDistrictId, request.microdistrictId,
            request.excludes
        ).map {
            Response(it)
        }

    data class Request(
        val localityId: UUID, val localityDistrictId: UUID? = null,
        val microdistrictId: UUID? = null, val excludes: List<UUID> = emptyList()
    ) : UseCase.Request

    data class Response(val streets: List<GeoStreet>) : UseCase.Response
}