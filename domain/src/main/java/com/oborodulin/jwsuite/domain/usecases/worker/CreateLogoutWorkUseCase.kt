package com.oborodulin.jwsuite.domain.usecases.worker

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import kotlinx.coroutines.flow.flow

class CreateLogoutWorkUseCase(
    configuration: Configuration, private val workerRepository: WorkerProviderRepository
) : UseCase<CreateLogoutWorkUseCase.Request, CreateLogoutWorkUseCase.Response>(configuration) {
    override fun process(request: Request) = flow { workerRepository.createLogoutWork(); emit(Response) }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}