package com.oborodulin.jwsuite.domain.usecases

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class GetPayerUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<GetPayerUseCase.Request, GetPayerUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        congregationsRepository.get(request.id).map {
            Response(it)
        }

    data class Request(val id: UUID) : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}