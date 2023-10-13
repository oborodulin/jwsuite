package com.oborodulin.jwsuite.domain.usecases.worker

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import kotlinx.coroutines.flow.flow

class CreateWorkUseCase(
    configuration: Configuration, private val workerRepository: WorkerProviderRepository
) : UseCase<CreateWorkUseCase.Request, CreateWorkUseCase.Response>(configuration) {
    override fun process(request: Request) = flow { workerRepository.createWork(); emit(Response) }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}