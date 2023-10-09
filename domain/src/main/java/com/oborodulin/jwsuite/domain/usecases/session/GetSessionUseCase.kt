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
            sessionManagerRepository.isLogged(),
            sessionManagerRepository.roles()
        ) { isSigned, isLogged, roles ->
            Response(Session(isSigned = isSigned, isLogged = isLogged, roles = roles))
        }

    data object Request : UseCase.Request
    data class Response(val session: Session) : UseCase.Response
}