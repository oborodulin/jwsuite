package com.oborodulin.jwsuite.domain.usecases

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SavePayerUseCase(
    configuration: Configuration,
    private val congregationsRepository: CongregationsRepository
) : UseCase<SavePayerUseCase.Request, SavePayerUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return congregationsRepository.save(request.congregation)
            .map {
                Response(it)
            }.catch { throw UseCaseException.PayerSaveException(it) }
    }

    data class Request(val congregation: Congregation) : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}
