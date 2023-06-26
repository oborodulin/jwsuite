package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionDistrictsUseCase(
    configuration: Configuration,
    private val regionDistrictsRepository: GeoRegionDistrictsRepository
) : UseCase<GetRegionDistrictsUseCase.Request, GetRegionDistrictsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        regionDistrictsRepository.getAllByRegion(request.regionId).map {
            Response(it)
        }

    data class Request(val regionId: UUID) : UseCase.Request
    data class Response(val regionDistricts: List<GeoRegionDistrict>) : UseCase.Response
}