package com.oborodulin.jwsuite.domain.usecases.worker

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import kotlinx.coroutines.flow.map

class LogoutWorkerOnSuccessUseCase(
    configuration: Configuration, private val workerRepository: WorkerProviderRepository
) : UseCase<LogoutWorkerOnSuccessUseCase.Request, LogoutWorkerOnSuccessUseCase.Response>(configuration) {
    override fun process(request: Request) = workerRepository.onLogoutWorkerSuccess().map { Response }

    data object Request : UseCase.Request
    data object Response : UseCase.Response
}