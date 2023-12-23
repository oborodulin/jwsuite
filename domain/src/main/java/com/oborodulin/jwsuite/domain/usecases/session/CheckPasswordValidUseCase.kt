package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

class CheckPasswordValidUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<CheckPasswordValidUseCase.Request, CheckPasswordValidUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    //.map { Response(it) }
    override fun process(request: Request) =
        sessionManagerRepository.isPasswordValid(request.password).map { Response(it) }

    data class Request(val password: String) : UseCase.Request
    data class Response(val isPasswordValid: Boolean) : UseCase.Response
}