package com.oborodulin.jwsuite.domain.usecases.geocountry

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteCountryUseCase(
    configuration: Configuration, private val countriesRepository: GeoCountriesRepository
) : UseCase<DeleteCountryUseCase.Request, DeleteCountryUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> {
        return countriesRepository.delete(request.countryId).map { Response }
    }

    data class Request(val countryId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
