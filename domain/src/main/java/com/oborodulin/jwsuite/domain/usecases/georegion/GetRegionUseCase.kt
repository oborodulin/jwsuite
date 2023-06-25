package com.oborodulin.jwsuite.domain.usecases.georegion

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetRegionUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetRegionUseCase.Request, GetRegionUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.get(request.congregationId).map {
            Response(it)
        }

    data class Request(val congregationId: UUID) : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}