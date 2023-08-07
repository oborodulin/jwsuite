package com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteLocalityDistrictUseCase(
    configuration: Configuration,
    private val localityDistrictsRepository: GeoLocalityDistrictsRepository
) : UseCase<DeleteLocalityDistrictUseCase.Request, DeleteLocalityDistrictUseCase.Response>(
    configuration
) {

    override fun process(request: Request): Flow<Response> {
        return localityDistrictsRepository.deleteById(request.localityDistrictId)
            .map {
                Response
            }
    }

    data class Request(val localityDistrictId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
