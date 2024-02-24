package com.oborodulin.jwsuite.domain.usecases.geocountry

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCountriesUseCase(
    configuration: Configuration, private val countriesRepository: GeoCountriesRepository
) : UseCase<GetCountriesUseCase.Request, GetCountriesUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        countriesRepository.getAll().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val countries: List<GeoCountry>) : UseCase.Response
}