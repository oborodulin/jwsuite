package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetStreetUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository
) : UseCase<GetStreetUseCase.Request, GetStreetUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        streetsRepository.get(request.streetId).map {
            Response(it)
        }

    data class Request(val streetId: UUID) : UseCase.Request
    data class Response(val street: GeoStreet) : UseCase.Response
}