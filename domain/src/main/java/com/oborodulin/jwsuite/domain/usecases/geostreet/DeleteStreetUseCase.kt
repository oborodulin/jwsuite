package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteStreetUseCase(
    configuration: Configuration, private val streetsRepository: GeoStreetsRepository
) : UseCase<DeleteStreetUseCase.Request, DeleteStreetUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return streetsRepository.delete(request.streetId)
            .map {
                Response
            }
    }

    data class Request(val streetId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
