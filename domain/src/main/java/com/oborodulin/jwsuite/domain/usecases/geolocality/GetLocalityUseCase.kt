package com.oborodulin.jwsuite.domain.usecases.geolocality

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetLocalityUseCase(
    configuration: Configuration,
    private val countriesRepository: GeoCountriesRepository,
    private val localitiesRepository: GeoLocalitiesRepository
) : UseCase<GetLocalityUseCase.Request, GetLocalityUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.localityId) {
        null -> countriesRepository.getDefault()
            .map { defCountry -> GeoLocality.default(defCountry) }

        else -> localitiesRepository.get(request.localityId)
    }.map {
        Response(it)
    }

    data class Request(val localityId: UUID? = null) : UseCase.Request
    data class Response(val locality: GeoLocality) : UseCase.Response
}