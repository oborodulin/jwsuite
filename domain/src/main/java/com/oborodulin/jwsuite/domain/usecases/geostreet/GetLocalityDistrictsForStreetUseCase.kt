package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoStreetWithLocalityDistricts
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class GetLocalityDistrictsForStreetUseCase(
    configuration: Configuration,
    private val streetsRepository: GeoStreetsRepository,
    private val localityDistrictsRepository: GeoLocalityDistrictsRepository
) : UseCase<GetLocalityDistrictsForStreetUseCase.Request, GetLocalityDistrictsForStreetUseCase.Response>(
    configuration
) {
    override fun process(request: Request): Flow<Response> = combine(
        streetsRepository.get(request.streetId),
        localityDistrictsRepository.getAllForStreet(request.streetId)
    ) { street, localityDistricts ->
        Response(
            GeoStreetWithLocalityDistricts(
                street = street, localityDistricts = localityDistricts
            )
        )
    }

    data class Request(val streetId: UUID) : UseCase.Request
    data class Response(val streetWithLocalityDistricts: GeoStreetWithLocalityDistricts) :
        UseCase.Response
}