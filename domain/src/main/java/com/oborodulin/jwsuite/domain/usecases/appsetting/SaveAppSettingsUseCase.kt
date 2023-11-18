package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveAppSettingsUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<SaveAppSettingsUseCase.Request, SaveAppSettingsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return appSettingsRepository.save(request.settings).map {
            Response(it)
        }.catch { throw UseCaseException.AppSettingsSaveException(it) }
    }

    data class Request(val settings: List<AppSetting>) : UseCase.Request
    data class Response(val settings: List<AppSetting>) : UseCase.Response
}
