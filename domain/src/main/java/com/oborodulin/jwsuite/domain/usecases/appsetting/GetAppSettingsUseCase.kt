package com.oborodulin.jwsuite.domain.usecases.appsetting

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.util.getAppVersion
import com.oborodulin.jwsuite.domain.model.appsetting.AppSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.combine

class GetAppSettingsUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository,
    private val sessionManagerRepository: SessionManagerRepository
) : UseCase<GetAppSettingsUseCase.Request, GetAppSettingsUseCase.Response>(configuration) {

    override fun process(request: Request) = combine(
        appSettingsRepository.getAll(),
        sessionManagerRepository.username(),
        sessionManagerRepository.roles()
    ) { settings, username, roles ->
        val version = ctx.getAppVersion()
        Response(
            AppSettingsWithSession(
                settings = settings,
                username = username.orEmpty(),
                roles = roles,
                versionName = version?.versionName.orEmpty()
            )
        )
    }

    data object Request : UseCase.Request
    data class Response(val appSettingsWithSession: AppSettingsWithSession) : UseCase.Response
}