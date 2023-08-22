package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SaveAppSettingUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<SaveAppSettingUseCase.Request, SaveAppSettingUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        return appSettingsRepository.save(request.setting).map {
            Response(it)
        }.catch { throw UseCaseException.AppSettingSaveException(it) }
    }

    data class Request(val setting: AppSetting) : UseCase.Request
    data class Response(val setting: AppSetting) : UseCase.Response
}
