package com.oborodulin.jwsuite.domain.usecases.congregation

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val TAG = "Domain.SaveCongregationUseCase"

class SaveCongregationUseCase(
    configuration: Configuration, private val congregationsRepository: CongregationsRepository
) : UseCase<SaveCongregationUseCase.Request, SaveCongregationUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        Timber.tag(TAG).d("process(...) called: request = %s", request.congregation)
        return congregationsRepository.save(request.congregation)
            .map {
                Response(it)
            }.catch {
                Timber.tag(TAG).d("process(...) error: %s", it.message)
                throw UseCaseException.CongregationSaveException(it)
            }
    }

    data class Request(val congregation: Congregation) : UseCase.Request
    data class Response(val congregation: Congregation) : UseCase.Response
}
