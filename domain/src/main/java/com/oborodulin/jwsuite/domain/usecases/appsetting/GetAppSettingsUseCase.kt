package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.AppSetting
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAppSettingsUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<GetAppSettingsUseCase.Request, GetAppSettingsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = appSettingsRepository.getAll().map {
        Response(it)
    }

    data object Request : UseCase.Request

    data class Response(val settings: List<AppSetting>) : UseCase.Response
}