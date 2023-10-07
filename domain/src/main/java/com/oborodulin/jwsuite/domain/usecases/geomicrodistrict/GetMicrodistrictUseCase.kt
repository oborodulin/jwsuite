package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetMicrodistrictUseCase(
    configuration: Configuration,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<GetMicrodistrictUseCase.Request, GetMicrodistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        microdistrictsRepository.get(request.microdistrictId).map {
            Response(it)
        }

    data class Request(val microdistrictId: UUID) : UseCase.Request
    data class Response(val microdistrict: GeoMicrodistrict) : UseCase.Response
}