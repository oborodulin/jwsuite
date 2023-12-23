package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.session.Session
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SignupUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
    //, private val membersRepository: MembersRepository
) : UseCase<SignupUseCase.Request, SignupUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        sessionManagerRepository.signup(request.username, request.password).map {//flatMapLatest {
            val isSigned = sessionManagerRepository.isSigned().first()
            Response(Session(isSigned = isSigned))
            /*combine(
                sessionManagerRepository.isSigned(),
                //sessionManagerRepository.isLogged(),
                membersRepository.getRoles(request.username)
            ) { isSigned, roles -> //isLogged, roles ->
                Response(
                    Session(
                        isSigned = isSigned, //isLogged = isLogged,
                        roles = roles
                    )
                )
            }*/
        }

    data class Request(val username: String, val password: String) : UseCase.Request
    data class Response(val session: Session) : UseCase.Response
}