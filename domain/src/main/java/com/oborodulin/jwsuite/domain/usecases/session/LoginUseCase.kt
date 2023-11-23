package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class LoginUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<LoginUseCase.Request, LoginUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    //.map { Response(it) }
    override fun process(request: Request) =
        sessionManagerRepository.login(request.password).flatMapLatest { username ->
            combine(
                sessionManagerRepository.isSigned(),
                sessionManagerRepository.lastDestination(),
                membersRepository.getRoles(username.orEmpty())
            ) { isSigned, lastDestination, roles ->
                Response(
                    Session(
                        isSigned = isSigned,
                        isLogged = username != null,
                        lastDestination = lastDestination,
                        roles = roles
                    )
                )
            }
        }

    data class Request(val password: String) : UseCase.Request

    //data class Response(val isLogged: Boolean) : UseCase.Response
    data class Response(val session: Session) : UseCase.Response
}