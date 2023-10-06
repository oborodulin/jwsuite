package com.oborodulin.jwsuite.domain.usecases.user

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val TAG = "Domain.SignupUseCase"

class SignupUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<SignupUseCase.Request, SignupUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        Timber.tag(TAG).d("process(...) called")
        return sessionManagerRepository.signup(request.username, request.password).map { Response }
    }

    data class Request(val username: String, val password: String) : UseCase.Request
    data object Response : UseCase.Response
}