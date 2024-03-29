package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

class LogoutUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<LogoutUseCase.Request, LogoutUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        sessionManagerRepository.logout(request.lastDestination).map { Response }
    /*            .flatMapLatest {
                combine(
                    sessionManagerRepository.isSigned(),
                    sessionManagerRepository.isLogged(),
                    sessionManagerRepository.roles()
                ) { isSigned, isLogged, roles ->
                    Response(Session(isSigned = isSigned, isLogged = isLogged, roles = roles))
                }
            }*/

    data class Request(val lastDestination: String? = null) : UseCase.Request
    data object Response : UseCase.Response
    //data class Response(val session: Session) : UseCase.Response
}