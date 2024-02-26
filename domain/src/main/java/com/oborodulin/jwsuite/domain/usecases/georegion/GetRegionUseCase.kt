package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoCountriesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionUseCase(
    configuration: Configuration,
    private val countriesRepository: GeoCountriesRepository,
    private val regionsRepository: GeoRegionsRepository
) : UseCase<GetRegionUseCase.Request, GetRegionUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.regionId) {
        null -> countriesRepository.getDefault().map { defCountry -> GeoRegion.default(defCountry) }
        else -> regionsRepository.get(request.regionId)
    }.map {
        Response(it)
    }

    data class Request(val regionId: UUID? = null) : UseCase.Request
    data class Response(val region: GeoRegion) : UseCase.Response
}