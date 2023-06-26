package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteLocalityUseCase(
    configuration: Configuration,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<DeleteLocalityUseCase.Request, DeleteLocalityUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return localitiesRepository.deleteById(request.localityId)
            .map {
                Response
            }
    }

    data class Request(val localityId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
