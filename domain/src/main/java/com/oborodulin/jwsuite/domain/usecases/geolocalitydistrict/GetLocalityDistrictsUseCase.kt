package com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetLocalityDistrictsUseCase(
    configuration: Configuration,
    private val localityDistrictsRepository: GeoLocalityDistrictsRepository
) : UseCase<GetLocalityDistrictsUseCase.Request, GetLocalityDistrictsUseCase.Response>(configuration) {

    override fun process(request: Request) = when (request.streetId) {
        null -> when (request.localityId) {
            null -> localityDistrictsRepository.getAll()
            else -> localityDistrictsRepository.getAllByLocality(request.localityId)
        }

        else -> localityDistrictsRepository.getAllByStreet(request.streetId)
    }.map {
        Response(it)
    }

    data class Request(val localityId: UUID? = null, val streetId: UUID? = null) : UseCase.Request
    data class Response(val localityDistricts: List<GeoLocalityDistrict>) : UseCase.Response
}