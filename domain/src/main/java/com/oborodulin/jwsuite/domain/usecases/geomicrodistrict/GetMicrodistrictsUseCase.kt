package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMicrodistrictsUseCase(
    configuration: Configuration,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<GetMicrodistrictsUseCase.Request, GetMicrodistrictsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = when (request.localityDistrictId) {
        null -> when (request.localityId) {
            null -> microdistrictsRepository.getAll()
            else -> microdistrictsRepository.getAllByLocality(request.localityId)
        }

        else -> microdistrictsRepository.getAllByLocalityDistrict(request.localityDistrictId)
    }.map {
        Response(it)
    }

    data class Request(val localityId: UUID? = null, val localityDistrictId: UUID? = null) :
        UseCase.Request

    data class Response(val microdistricts: List<GeoMicrodistrict>) : UseCase.Response
}