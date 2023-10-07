package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetLocalitiesUseCase(
    configuration: Configuration,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<GetLocalitiesUseCase.Request, GetLocalitiesUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.regionDistrictId) {
        null -> when (request.regionId) {
            null -> localitiesRepository.getAll()
            else -> localitiesRepository.getAllByRegion(request.regionId)
        }

        else -> localitiesRepository.getAllByRegionDistrict(request.regionDistrictId)
    }.map {
        Response(it)
    }

    data class Request(val regionId: UUID? = null, val regionDistrictId: UUID? = null) :
        UseCase.Request

    data class Response(val localities: List<GeoLocality>) : UseCase.Response
}