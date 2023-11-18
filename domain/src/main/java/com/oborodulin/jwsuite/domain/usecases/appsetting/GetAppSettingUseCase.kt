package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.map
import java.util.UUID

class GetAppSettingUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<GetAppSettingUseCase.Request, GetAppSettingUseCase.Response>(configuration) {

    override fun process(request: Request) = appSettingsRepository.get(request.settingId).map {
        Response(it)
    }

    data class Request(val settingId: UUID) : UseCase.Request
    data class Response(val setting: AppSetting) : UseCase.Response
}