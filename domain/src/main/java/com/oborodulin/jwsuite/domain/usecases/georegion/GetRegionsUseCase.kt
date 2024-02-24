package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionsUseCase(
    configuration: Configuration, private val regionsRepository: GeoRegionsRepository
) : UseCase<GetRegionsUseCase.Request, GetRegionsUseCase.Response>(configuration) {
    override fun process(request: Request) = when (request.countryId) {
        null -> regionsRepository.getAll()
        else -> regionsRepository.getAllByCountry(request.countryId)
    }.map {
        Response(it)
    }

    data class Request(val countryId: UUID? = null) : UseCase.Request
    data class Response(val regions: List<GeoRegion>) : UseCase.Response
}