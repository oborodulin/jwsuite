package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.combine

class GetSessionUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<GetSessionUseCase.Request, GetSessionUseCase.Response>(configuration) {
    override fun process(request: Request) =
        combine(
            sessionManagerRepository.isSigned(),
            //sessionManagerRepository.isLogged(),
            sessionManagerRepository.lastDestination(),
            sessionManagerRepository.roles()
        ) { isSigned, lastDestination, roles -> //isLogged, lastDestination, roles ->
            Response(
                Session(
                    isSigned = isSigned, //isLogged = isLogged,
                    lastDestination = lastDestination, roles = roles
                )
            )
        }

    data object Request : UseCase.Request
    data class Response(val session: Session) : UseCase.Response
}