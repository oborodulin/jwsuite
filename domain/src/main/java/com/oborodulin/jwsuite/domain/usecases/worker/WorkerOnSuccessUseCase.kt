package com.oborodulin.jwsuite.domain.usecases.worker

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import kotlinx.coroutines.flow.map

class WorkerOnSuccessUseCase(
    configuration: Configuration, private val workerRepository: WorkerProviderRepository
) : UseCase<WorkerOnSuccessUseCase.Request, WorkerOnSuccessUseCase.Response>(configuration) {
    override fun process(request: Request) = workerRepository.onWorkerSuccess().map { Response }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}