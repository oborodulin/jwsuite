package com.oborodulin.jwsuite.domain.usecases.georegiondistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteRegionDistrictUseCase(
    configuration: Configuration,
    private val regionDistrictsRepository: GeoRegionDistrictsRepository
) : UseCase<DeleteRegionDistrictUseCase.Request, DeleteRegionDistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return regionDistrictsRepository.deleteById(request.regionDistrictId)
            .map {
                Response
            }
    }

    data class Request(val regionDistrictId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
