package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionDistrictUseCase(
    configuration: Configuration,
    private val regionDistrictsRepository: GeoRegionDistrictsRepository
) : UseCase<GetRegionDistrictUseCase.Request, GetRegionDistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        regionDistrictsRepository.get(request.regionDistrictId).map {
            Response(it)
        }

    data class Request(val regionDistrictId: UUID) : UseCase.Request
    data class Response(val regionDistrict: GeoRegionDistrict) : UseCase.Response
}