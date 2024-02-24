package com.oborodulin.jwsuite.domain.usecases.geocountry

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetCountryUseCase(
    configuration: Configuration, private val countriesRepository: GeoCountriesRepository
) : UseCase<GetCountryUseCase.Request, GetCountryUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        countriesRepository.get(request.countryId).map { Response(it) }

    data class Request(val countryId: UUID) : UseCase.Request
    data class Response(val country: GeoCountry) : UseCase.Response
}