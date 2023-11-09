package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

class LoginUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<LoginUseCase.Request, LoginUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        sessionManagerRepository.login(request.password).map { Response(it) }
    /*.flatMapLatest {
    combine(
        sessionManagerRepository.isSigned(),
        sessionManagerRepository.isLogged(),
        sessionManagerRepository.lastDestination(),
        sessionManagerRepository.roles()
    ) { isSigned, isLogged, lastDestination, roles ->
        Response(
            Session(
                isSigned = isSigned, isLogged = isLogged, lastDestination = lastDestination,
                roles = roles
            )
        )
    }
}*/

    data class Request(val password: String) : UseCase.Request
    data class Response(val isLogged: Boolean) : UseCase.Response
    //data class Response(val session: Session) : UseCase.Response
}