package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMicrodistrictUseCase(
    configuration: Configuration,
    private val countriesRepository: GeoCountriesRepository,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<GetMicrodistrictUseCase.Request, GetMicrodistrictUseCase.Response>(configuration) {

    override fun process(request: Request) = when (request.microdistrictId) {
        null -> countriesRepository.getDefault().map { defCountry ->
            GeoMicrodistrict.default(country = defCountry)
        }

        else -> microdistrictsRepository.get(request.microdistrictId)
    }.map {
        Response(it)
    }

    data class Request(val microdistrictId: UUID? = null) : UseCase.Request
    data class Response(val microdistrict: GeoMicrodistrict) : UseCase.Response
}