package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetLocalityUseCase(
    configuration: Configuration,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<GetLocalityUseCase.Request, GetLocalityUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        localitiesRepository.get(request.localityId).map {
            Response(it)
        }

    data class Request(val localityId: UUID) : UseCase.Request
    data class Response(val locality: GeoLocality) : UseCase.Response
}