package com.oborodulin.jwsuite.domain.usecases.geocountry

import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetCountriesUseCase(
    configuration: Configuration, private val countriesRepository: GeoCountriesRepository
) : UseCase<GetCountriesUseCase.Request, GetCountriesUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        countriesRepository.getAll(request.isRemoteFetch).map {
            when (it) {
                is Result.Success<List<GeoCountry>> -> Response(it.data)
                else -> Response(emptyList())
            }
        }.catch { throw UseCaseException.GeoCountryApiException(it) }

    data class Request(val isRemoteFetch: Boolean = false) : UseCase.Request
    data class Response(val countries: List<GeoCountry>) : UseCase.Response
}