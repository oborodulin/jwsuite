package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.map

class LogoutUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<LogoutUseCase.Request, LogoutUseCase.Response>(configuration) {
    override fun process(request: Request) = sessionManagerRepository.logout().map { Response }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}