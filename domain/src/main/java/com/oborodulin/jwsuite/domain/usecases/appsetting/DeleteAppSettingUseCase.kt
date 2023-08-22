package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class DeleteAppSettingUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<DeleteAppSettingUseCase.Request, DeleteAppSettingUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return appSettingsRepository.deleteById(request.settingId)
            .map {
                Response
            }
    }

    data class Request(val settingId: UUID) : UseCase.Request
    object Response : UseCase.Response
}
