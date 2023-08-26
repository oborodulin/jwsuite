package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetStreetsForTerritoryUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository
) : UseCase<GetStreetsForTerritoryUseCase.Request, GetStreetsForTerritoryUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.microdistrictId) {
        null -> when (request.localityDistrictId) {
            null -> when (request.localityId) {
                null -> streetsRepository.getAll()
                else -> streetsRepository.getAllByLocality(
                    request.localityId, request.isPrivateSector
                )
            }

            else -> streetsRepository.getAllByLocalityDistrict(
                request.localityDistrictId, request.isPrivateSector
            )
        }

        else -> streetsRepository.getAllByMicrodistrict(
            request.microdistrictId, request.isPrivateSector
        )
    }.map {
        Response(it)
    }

    data class Request(
        val localityId: UUID? = null, val localityDistrictId: UUID? = null,
        val microdistrictId: UUID? = null, val excludes: List<UUID> = emptyList()
    ) : UseCase.Request

    data class Response(val streets: List<GeoStreet>) : UseCase.Response
}