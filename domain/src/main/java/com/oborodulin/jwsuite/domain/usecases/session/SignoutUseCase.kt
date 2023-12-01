package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SignoutUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<SignoutUseCase.Request, SignoutUseCase.Response>(configuration) {
    override fun process(request: Request) = sessionManagerRepository.signout().map {
        val isSigned = sessionManagerRepository.isSigned().first()
        Response(Session(isSigned = isSigned, isLogged = false))
    }

    data object Request : UseCase.Request
    data class Response(val session: Session) : UseCase.Response
    //data object Response : UseCase.Response
}