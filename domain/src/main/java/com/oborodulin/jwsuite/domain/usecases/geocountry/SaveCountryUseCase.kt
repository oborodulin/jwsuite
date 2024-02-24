package com.oborodulin.jwsuite.domain.usecases.geocountry

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveCountryUseCase(
    configuration: Configuration, private val countriesRepository: GeoCountriesRepository
) : UseCase<SaveCountryUseCase.Request, SaveCountryUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return countriesRepository.save(request.country).map {
                Response(it)
            }.catch { throw UseCaseException.GeoCountrySaveException(it) }
    }

    data class Request(val country: GeoCountry) : UseCase.Request
    data class Response(val country: GeoCountry) : UseCase.Response
}
