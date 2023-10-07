package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoStreetWithMicrodistricts
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetMicrodistrictsForStreetUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<GetMicrodistrictsForStreetUseCase.Request, GetMicrodistrictsForStreetUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> = combine(
        streetsRepository.get(request.streetId),
        microdistrictsRepository.getAllForStreet(request.streetId)
    ) { street, microdistricts ->
        Response(GeoStreetWithMicrodistricts(street = street, microdistricts = microdistricts))
    }

    data class Request(val streetId: UUID) : UseCase.Request
    data class Response(val streetWithMicrodistricts: GeoStreetWithMicrodistricts) :
        UseCase.Response
}