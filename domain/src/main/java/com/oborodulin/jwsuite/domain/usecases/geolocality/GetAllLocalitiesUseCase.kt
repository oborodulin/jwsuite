package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.map

class GetAllLocalitiesUseCase(
    configuration: Configuration,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<GetAllLocalitiesUseCase.Request, GetAllLocalitiesUseCase.Response>(configuration) {

    override fun process(request: Request) = localitiesRepository.getAll().map {
        Response(it)
    }

    object Request : UseCase.Request
    data class Response(val localities: List<GeoLocality>) : UseCase.Response
}