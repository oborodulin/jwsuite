package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteStreetMicrodistrictUseCase(
    configuration: Configuration, private val streetsRepository: GeoStreetsRepository
) : UseCase<DeleteStreetMicrodistrictUseCase.Request, DeleteStreetMicrodistrictUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.deleteLocalityDistrict(
            request.streetId, request.microdistrictId
        ).map { Response }
    }

    data class Request(val streetId: UUID, val microdistrictId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
