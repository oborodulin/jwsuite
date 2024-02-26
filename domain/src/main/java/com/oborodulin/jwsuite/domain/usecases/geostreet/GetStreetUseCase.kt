package com.oborodulin.jwsuite.domain.usecases.geostreet

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetStreetUseCase(
    configuration: Configuration,
    private val countriesRepository: GeoCountriesRepository,
    private val streetsRepository: GeoStreetsRepository
) : UseCase<GetStreetUseCase.Request, GetStreetUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.streetId) {
        null -> countriesRepository.getDefault().map { defCountry ->
            GeoStreet.default(country = defCountry)
        }

        else -> streetsRepository.get(request.streetId)
    }.map {
        Response(it)
    }

    data class Request(val streetId: UUID? = null) : UseCase.Request
    data class Response(val street: GeoStreet) : UseCase.Response
}