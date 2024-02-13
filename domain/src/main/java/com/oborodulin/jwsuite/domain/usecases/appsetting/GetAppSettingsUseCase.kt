package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import kotlinx.coroutines.flow.map

class GetAppSettingsUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<GetAppSettingsUseCase.Request, GetAppSettingsUseCase.Response>(configuration) {
    override fun process(request: Request) = when {
        request.paramNames.isNotEmpty() -> appSettingsRepository.getAllByNames(request.paramNames)
        else -> appSettingsRepository.getAll()
    }.map { Response(it) }

    data class Request(val paramNames: List<AppSettingParam> = emptyList()) : UseCase.Request
    data class Response(val settings: List<AppSetting>) : UseCase.Response
}