package com.oborodulin.jwsuite.domain.usecases.appsetting

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.util.getAppVersion
import com.oborodulin.jwsuite.domain.model.appsetting.AppSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class GetAppSettingsUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<GetAppSettingsUseCase.Request, GetAppSettingsUseCase.Response>(configuration) {

    override fun process(request: Request) = combine(
        appSettingsRepository.getAll(),
        sessionManagerRepository.username(),
        appSettingsRepository.sqliteVersion(),
        appSettingsRepository.dbVersion()
    ) { settings, username, sqliteVersion, dbVersion ->
        val roles = membersRepository.getMemberRoles(username.orEmpty()).first()
        val roleTransferObjects = membersRepository.getMemberTransferObjects(username.orEmpty()).first()
        val version = ctx.getAppVersion()
        Response(
            AppSettingsWithSession(
                settings = settings,
                username = username.orEmpty(),
                roles = roles,
                roleTransferObjects = roleTransferObjects,
                appVersionName = version?.versionName.orEmpty(),
                frameworkVersion = "${android.os.Build.VERSION.SDK_INT}",
                sqliteVersion = sqliteVersion,
                dbVersion = dbVersion
            )
        )
    }

    data object Request : UseCase.Request
    data class Response(val appSettingsWithSession: AppSettingsWithSession) : UseCase.Response
}