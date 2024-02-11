package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteMicrodistrictUseCase(
    configuration: Configuration,
    private val microdistrictsRepository: GeoMicrodistrictsRepository
) : UseCase<DeleteMicrodistrictUseCase.Request, DeleteMicrodistrictUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return microdistrictsRepository.delete(request.microdistrictId)
            .map {
                Response
            }
    }

    data class Request(val microdistrictId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
