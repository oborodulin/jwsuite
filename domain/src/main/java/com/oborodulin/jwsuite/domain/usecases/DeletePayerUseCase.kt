package com.oborodulin.jwsuite.domain.usecases

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class DeletePayerUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<DeletePayerUseCase.Request, DeletePayerUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return congregationsRepository.deleteById(request.payerId)
            .map {
                Response
            }
    }

    data class Request(val payerId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
