package com.oborodulin.jwsuite.domain.usecases.session

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.map

class SignoutUseCase(
    configuration: Configuration, private val sessionManagerRepository: SessionManagerRepository
) : UseCase<SignoutUseCase.Request, SignoutUseCase.Response>(configuration) {
    override fun process(request: Request) = sessionManagerRepository.signout().map { Response }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}