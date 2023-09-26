package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteStreetLocalityDistrictUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository
) : UseCase<DeleteStreetLocalityDistrictUseCase.Request, DeleteStreetLocalityDistrictUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.deleteLocalityDistrict(
            request.streetId, request.localityDistrictId
        ).map { Response }
    }

    data class Request(val streetId: UUID, val localityDistrictId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
