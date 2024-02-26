package com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetLocalityDistrictUseCase(
    configuration: Configuration,
    private val countriesRepository: GeoCountriesRepository,
    private val localityDistrictsRepository: GeoLocalityDistrictsRepository
) : UseCase<GetLocalityDistrictUseCase.Request, GetLocalityDistrictUseCase.Response>(configuration) {

    override fun process(request: Request) = when (request.localityDistrictId) {
        null -> countriesRepository.getDefault()
            .map { defCountry -> GeoLocalityDistrict.default(country = defCountry) }

        else -> localityDistrictsRepository.get(request.localityDistrictId)
    }.map {
        Response(it)
    }

    data class Request(val localityDistrictId: UUID? = null) : UseCase.Request
    data class Response(val localityDistrict: GeoLocalityDistrict) : UseCase.Response
}