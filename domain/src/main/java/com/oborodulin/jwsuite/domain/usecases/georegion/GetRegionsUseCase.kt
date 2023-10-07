package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRegionsUseCase(
    configuration: Configuration,
    private val regionsRepository: GeoRegionsRepository
) : UseCase<GetRegionsUseCase.Request, GetRegionsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        regionsRepository.getAll().map {
            Response(it)
        }

    object Request : UseCase.Request
    data class Response(val regions: List<GeoRegion>) : UseCase.Response
}