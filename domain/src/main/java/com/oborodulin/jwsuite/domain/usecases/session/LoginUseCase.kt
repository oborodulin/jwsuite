package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class LoginUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<LoginUseCase.Request, LoginUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        sessionManagerRepository.login(request.password).flatMapLatest {
            combine(
                sessionManagerRepository.isSigned(),
                sessionManagerRepository.isLogged(),
                sessionManagerRepository.roles()
            ) { isSigned, isLogged, roles ->
                Response(Session(isSigned = isSigned, isLogged = isLogged, roles = roles))
            }
        }

    data class Request(val password: String) : UseCase.Request
    data class Response(val session: Session) : UseCase.Response
}